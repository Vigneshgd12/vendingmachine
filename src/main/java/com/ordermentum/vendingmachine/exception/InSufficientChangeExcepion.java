package com.ordermentum.vendingmachine.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class InSufficientChangeExcepion extends Throwable {
    private String message;
}
