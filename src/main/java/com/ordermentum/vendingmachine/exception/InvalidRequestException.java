package com.ordermentum.vendingmachine.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InvalidRequestException extends RuntimeException {
    private String message;
}
