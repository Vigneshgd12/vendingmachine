package com.ordermentum.vendingmachine.exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OutOfStockException  extends RuntimeException {
    private String message;
}
