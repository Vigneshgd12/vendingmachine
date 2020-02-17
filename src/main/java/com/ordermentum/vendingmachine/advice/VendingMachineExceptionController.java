package com.ordermentum.vendingmachine.advice;


import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.dto.ReturnChangeDTO;
import com.ordermentum.vendingmachine.exception.MaximumInputAmountExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VendingMachineExceptionController {


    @ExceptionHandler(value = MaximumInputAmountExceededException.class)
    public ResponseEntity<ReturnChangeDTO> exception(MaximumInputAmountExceededException exception) {
        AddLocalBalanceDTO balance = exception.getLocalBalanceDTO();
        ReturnChangeDTO changeDTO = ReturnChangeDTO.builder()
                .tenCents(balance.getTenCents())
                .twentyCents(balance.getTwentyCents())
                .fiftyCents(balance.getFiftyCents())
                .oneDollar(balance.getOneDollar())
                .twoDollars(balance.getTwoDollars())
                .build();
        return new ResponseEntity<>(changeDTO, HttpStatus.BAD_REQUEST);
    }
}
