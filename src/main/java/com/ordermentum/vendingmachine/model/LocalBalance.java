package com.ordermentum.vendingmachine.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LocalBalance {

    private double localBalanceValue;
    private int tenCents;
    private int twentyCents;
    private int fiftyCents;
    private int oneDollar;
    private int twoDollars;
}
