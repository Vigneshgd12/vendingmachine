package com.ordermentum.vendingmachine.delegates;

import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.dto.ChocolateSaleInvoiceDTO;
import com.ordermentum.vendingmachine.dto.ReturnChangeDTO;
import com.ordermentum.vendingmachine.exception.InSufficientChangeExcepion;
import com.ordermentum.vendingmachine.exception.InvalidRequestException;
import com.ordermentum.vendingmachine.exception.LowBalanceException;
import com.ordermentum.vendingmachine.exception.MaximumInputAmountExceededException;
import com.ordermentum.vendingmachine.helper.ReturnChangeHelper;
import com.ordermentum.vendingmachine.model.ChocolateDetail;
import com.ordermentum.vendingmachine.model.LocalBalance;
import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ordermentum.vendingmachine.helper.TransactionHelper.*;
import static com.ordermentum.vendingmachine.constants.VendingMachineConstants.*;
@Component
public class VendingDelegate {

    @Autowired
    private VendingMachineService service;

    public void addLocalBalance(String id, AddLocalBalanceDTO localBalance) {
        VendingMachine machine = service.getVendingMachineById(id);
        LocalBalance updatedLocalBalance = generateLocalBalanceObject(machine.getLocalBalance(),localBalance);
        if(updatedLocalBalance.getLocalBalanceValue()>50){
            throw new MaximumInputAmountExceededException();
        }
        machine.setLocalBalance(updatedLocalBalance);
        service.updateVendingMachine(machine);
    }


    public ChocolateSaleInvoiceDTO sellChocolate(String id, String chocolateId) {
        VendingMachine machine = service.getVendingMachineById(id);
        ChocolateSaleInvoiceDTO invoice = ChocolateSaleInvoiceDTO.builder().build();
        ChocolateDetail chocolate = machine.getChocolateDetails()
                .stream()
                .filter(chocolateDetail -> chocolateDetail.getId().equals(chocolateId))
                .findFirst().orElseThrow(()-> new InvalidRequestException());
        machine.getChocolateDetails().removeIf(chocolateDetail -> chocolateDetail.getId().equals(chocolateId));
        return validateAndProcessChocolateSale(chocolateId, machine, invoice, chocolate);
    }

    private ChocolateSaleInvoiceDTO validateAndProcessChocolateSale(String chocolateId, VendingMachine machine, ChocolateSaleInvoiceDTO invoice, ChocolateDetail chocolate) {
        double localBalance = machine.getLocalBalance().getLocalBalanceValue();
        ReturnChangeDTO changeDTO = resetReturnChange();
        if(chocolate.getPriceOfEach()<=localBalance) {
            if (chocolate.getCurrentStock() == 0) {
                return outOfStockInvoice(machine, invoice, changeDTO);
            }else {
                double changeToBeReturned = roundOffDifference(localBalance - chocolate.getPriceOfEach());
                moveLocalBalanceToCoinsInStock(machine);
                boolean processDistribution = true;
                if (changeToBeReturned > 0) {
                    processDistribution = checkProcessDistribution(chocolateId, machine, invoice, changeToBeReturned, changeDTO);
                }
                if (processDistribution) {
                    chocolate.setCurrentStock(chocolate.getCurrentStock() - 1);
                    chocolate.setTotalCurrentValue(roundOffDifference(chocolate.getTotalCurrentValue() - chocolate.getPriceOfEach()));
                }
                machine.getChocolateDetails().add(chocolate);
                machine.setLocalBalance(resetLocalBalance());
                service.updateVendingMachine(machine);
                invoice.setChange(changeDTO);
                return invoice;
            }
        }else{
            throw new LowBalanceException();
        }
    }

    private ChocolateSaleInvoiceDTO outOfStockInvoice(VendingMachine machine, ChocolateSaleInvoiceDTO invoice, ReturnChangeDTO changeDTO) {
        machine.setLocalBalance(resetLocalBalance());
        service.updateVendingMachine(machine);
        returnLocalBalanceAsChange(machine, changeDTO);
        invoice.setMessage("The selected chocolate is not in stock. Please select different chocolate");
        invoice.setChange(changeDTO);
        return invoice;
    }

    private boolean checkProcessDistribution(String chocolateId, VendingMachine machine,
                                             ChocolateSaleInvoiceDTO invoice, double changeToBeReturned,
                                             ReturnChangeDTO changeDTO) {
        try {
            ReturnChangeHelper.validateChangeAvailablity(machine.getCoinsInStock(),changeDTO,changeToBeReturned);
            invoice.setMessage("Chocolate "+ chocolateId+" has been successfully delivered");
        } catch (InSufficientChangeExcepion inSufficientChangeExcepion) {
            returnLocalBalanceAsChange(machine, changeDTO);
            removeLocalBalanceFromCoinsInStock(machine);
            invoice.setMessage("Sorry we have no change. Please provide exact amount of coins");
            return false;
        }
        return true;
    }
}
