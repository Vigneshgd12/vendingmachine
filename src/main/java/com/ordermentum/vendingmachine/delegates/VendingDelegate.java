package com.ordermentum.vendingmachine.delegates;

import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.dto.ChocolateSaleInvoiceDTO;
import com.ordermentum.vendingmachine.dto.ReturnChangeDTO;
import com.ordermentum.vendingmachine.exception.InSufficientChangeExcepion;
import com.ordermentum.vendingmachine.exception.InvalidRequestException;
import com.ordermentum.vendingmachine.exception.LowBalanceException;
import com.ordermentum.vendingmachine.exception.OutOfStockException;
import com.ordermentum.vendingmachine.helper.ReturnChangeHelper;
import com.ordermentum.vendingmachine.model.ChocolateDetail;
import com.ordermentum.vendingmachine.model.LocalBalance;
import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ordermentum.vendingmachine.helper.TransactionHelper.*;
@Component
public class VendingDelegate {

    @Autowired
    private VendingMachineService service;

    public void addLocalBalance(String id, AddLocalBalanceDTO localBalance) {
        VendingMachine machine = service.getVendingMachineById(id);
        LocalBalance updatedLocalBalance = generateLocalBalanceObject(machine.getLocalBalance(),localBalance);
        machine.setLocalBalance(updatedLocalBalance);
        service.updateVendingMachine(machine);
    }


    public ChocolateSaleInvoiceDTO sellChocolate(String id, String chocolateId) {
        VendingMachine machine = service.getVendingMachineById(id);
        String messageToConsole = null;
        ChocolateDetail chocolate = machine.getChocolateDetails()
                .stream()
                .filter(chocolateDetail -> chocolateDetail.getId().equals(chocolateId))
                .findFirst().orElseThrow(()-> new InvalidRequestException());
        machine.getChocolateDetails().removeIf(chocolateDetail -> chocolateDetail.getId().equals(chocolateId));
        double localBalance = machine.getLocalBalance().getLocalBalanceValue();
        if(chocolate.getPriceOfEach()>localBalance){
            throw new LowBalanceException();
        }else{
            if(chocolate.getCurrentStock()==0){
                throw new OutOfStockException();
            }
            double changeToBeReturned = Math.round(localBalance - chocolate.getPriceOfEach());
            moveLocalBalanceToCoinsInStock(machine);
            ReturnChangeDTO changeDTO = resetReturnChange();
            if(changeToBeReturned>0){
                try {
                    ReturnChangeHelper.validateChangeAvailablity(machine.getCoinsInStock(),changeDTO,changeToBeReturned);
                    chocolate.setCurrentStock(chocolate.getCurrentStock()-1);
                    chocolate.setTotalCurrentValue(chocolate.getTotalCurrentValue()-chocolate.getPriceOfEach());
                    machine.getChocolateDetails().add(chocolate);
                    messageToConsole = "Chocolate "+ chocolateId+" has been successfully delivered";
                } catch (InSufficientChangeExcepion inSufficientChangeExcepion) {
                    changeDTO.setTenCents(machine.getLocalBalance().getTenCents());
                    changeDTO.setTwentyCents(machine.getLocalBalance().getTwentyCents());
                    changeDTO.setFiftyCents(machine.getLocalBalance().getFiftyCents());
                    changeDTO.setOneDollar(machine.getLocalBalance().getOneDollar());
                    changeDTO.setTwoDollars(machine.getLocalBalance().getTwoDollars());
                    messageToConsole = "Sorry we have no change. Please provide exact amount of coins";
                }
            }
            machine.setLocalBalance(resetLocalBalance());
            service.updateVendingMachine(machine);
            return ChocolateSaleInvoiceDTO.builder().message(messageToConsole).change(changeDTO).build();
        }
    }
}
