package com.ordermentum.vendingmachine.exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MaximumInputAmountExceededException extends RuntimeException {
    private String message;
}
