package com.ordermentum.vendingmachine.helper;

import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.dto.ReturnChangeDTO;
import com.ordermentum.vendingmachine.model.CoinsInStock;
import com.ordermentum.vendingmachine.model.LocalBalance;
import com.ordermentum.vendingmachine.model.VendingMachine;

import static com.ordermentum.vendingmachine.constants.VendingMachineConstants.*;
public class TransactionHelper {

    public static LocalBalance generateLocalBalanceObject(LocalBalance balance, AddLocalBalanceDTO localBalanceDTO){
        if(null == balance){
            balance = resetLocalBalance();
        }
        return LocalBalance.builder()
                .tenCents(balance.getTenCents()+localBalanceDTO.getTenCents())
                .twentyCents(balance.getTwentyCents()+localBalanceDTO.getTwentyCents())
                .fiftyCents(balance.getFiftyCents()+localBalanceDTO.getFiftyCents())
                .oneDollar(balance.getOneDollar()+localBalanceDTO.getOneDollar())
                .twoDollars(balance.getTwoDollars()+localBalanceDTO.getTwoDollars())
                .localBalanceValue(balance.getLocalBalanceValue()+getLocalBalanceValue(localBalanceDTO))
                .build();
    }

    private static double getLocalBalanceValue(AddLocalBalanceDTO balance){
        return (balance.getTenCents()*TEN_CENTS_VALUE)+(balance.getTwentyCents()*TWENTY_CENTS_VALUE)+
                (balance.getFiftyCents()*FIFTY_CENTS_VALUE)+(balance.getOneDollar()*ONE_DOLLAR_VALUE)+
                (balance.getTwoDollars()*TWO_DOLLARS_VALUE);
    }

    public static LocalBalance resetLocalBalance(){
        return LocalBalance.builder().localBalanceValue(0).twoDollars(0).oneDollar(0).twentyCents(0).tenCents(0).fiftyCents(0).build();
    }


    public static ReturnChangeDTO resetReturnChange(){
        return ReturnChangeDTO.builder().twoDollars(0).oneDollar(0).twentyCents(0).tenCents(0).fiftyCents(0).build();
    }


    public static void moveLocalBalanceToCoinsInStock(VendingMachine machine){
        LocalBalance balance = machine.getLocalBalance();
        CoinsInStock inStock = machine.getCoinsInStock();
        machine.setCoinsInStock(
                CoinsInStock.builder()
                .tenCents(inStock.getTenCents()+balance.getTenCents())
                .twentyCents(inStock.getTwentyCents()+balance.getTwentyCents())
                .fiftyCents(inStock.getFiftyCents()+balance.getFiftyCents())
                .oneDollar(inStock.getOneDollar()+balance.getOneDollar())
                .twoDollars(inStock.getTwoDollars()+balance.getTwoDollars())
                .build()
        );
    }
}
