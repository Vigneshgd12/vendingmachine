package com.ordermentum.vendingmachine.exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LowBalanceException extends RuntimeException {
    private String message;
}
