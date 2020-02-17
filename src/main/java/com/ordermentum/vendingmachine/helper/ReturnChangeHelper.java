package com.ordermentum.vendingmachine.helper;


import com.ordermentum.vendingmachine.dto.ReturnChangeDTO;
import com.ordermentum.vendingmachine.exception.InSufficientChangeExcepion;
import com.ordermentum.vendingmachine.model.CoinsInStock;

import static com.ordermentum.vendingmachine.constants.VendingMachineConstants.*;
import static com .ordermentum.vendingmachine.helper.TransactionHelper.*;
public class ReturnChangeHelper {


    public static ReturnChangeDTO validateChangeAvailablity(CoinsInStock coinsInStock,
                                                            ReturnChangeDTO change,
                                                            double changeToBeReturned) throws InSufficientChangeExcepion {

            while(changeToBeReturned > 0 && coinsInStock.getTwoDollars() >0){
            double updatedChangeToBeReturned = roundOffDifference(changeToBeReturned - TWO_DOLLARS_VALUE *1);
            if(updatedChangeToBeReturned >=0 && coinsInStock.getTwoDollars()>0){
                change.setTwoDollars(change.getTwoDollars() + 1 );
                coinsInStock.setTwoDollars(coinsInStock.getTwoDollars() - 1 );
                changeToBeReturned = updatedChangeToBeReturned;
            }else{
                break;
            }
        }
        while(changeToBeReturned > 0 && coinsInStock.getOneDollar() >0){
            double updatedChangeToBeReturned = roundOffDifference(changeToBeReturned - ONE_DOLLAR_VALUE *1);
            if(updatedChangeToBeReturned >=0 && coinsInStock.getOneDollar()>0){
                change.setOneDollar(change.getOneDollar() + 1 );
                coinsInStock.setOneDollar(coinsInStock.getOneDollar() - 1 );
                changeToBeReturned = updatedChangeToBeReturned;
            }else{
                break;
            }
        }
        while(changeToBeReturned > 0 && coinsInStock.getFiftyCents() >0){
            double updatedChangeToBeReturned = roundOffDifference(changeToBeReturned - FIFTY_CENTS_VALUE *1);
            if(updatedChangeToBeReturned >=0 && coinsInStock.getFiftyCents()>0){
                change.setFiftyCents(change.getFiftyCents() + 1 );
                coinsInStock.setFiftyCents(coinsInStock.getFiftyCents() - 1 );
                changeToBeReturned = updatedChangeToBeReturned;
            }else{
                break;
            }
        }
        while(changeToBeReturned > 0 && coinsInStock.getTwentyCents() >0){
            double updatedChangeToBeReturned =  roundOffDifference(changeToBeReturned - TWENTY_CENTS_VALUE *1);
            if(updatedChangeToBeReturned >=0 && coinsInStock.getTwentyCents()>0){
                change.setTwentyCents(change.getTwentyCents() + 1 );
                coinsInStock.setTwentyCents(coinsInStock.getTwentyCents() - 1 );
                changeToBeReturned = updatedChangeToBeReturned;
            }else{
                break;
            }
        }
        while(changeToBeReturned > 0 && coinsInStock.getTenCents() >0){
            double updatedChangeToBeReturned =  roundOffDifference(changeToBeReturned - TEN_CENTS_VALUE *1);
            if(updatedChangeToBeReturned >=0 && coinsInStock.getTenCents()>0){
                change.setTenCents(change.getTenCents() + 1 );
                coinsInStock.setTenCents(coinsInStock.getTenCents() - 1 );
                changeToBeReturned = updatedChangeToBeReturned;
            }else{
                break;
            }
        }

        if(changeToBeReturned>0){
            moveReturnChangeToCoinsInStock(change, coinsInStock);
            throw new InSufficientChangeExcepion();
        }
        change.setTotalChangeReturned(change.getTenCents()*TEN_CENTS_VALUE+ change.getTwentyCents()*TWENTY_CENTS_VALUE+
                change.getFiftyCents()*FIFTY_CENTS_VALUE+ change.getOneDollar()*ONE_DOLLAR_VALUE+ change.getTwoDollars()*TWO_DOLLARS_VALUE);
        return change;
    }
}
