package com.ordermentum.vendingmachine.exception;
import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MaximumInputAmountExceededException extends RuntimeException {
    private String message;
    private AddLocalBalanceDTO localBalanceDTO;
}
