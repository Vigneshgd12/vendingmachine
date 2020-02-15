package com.ordermentum.vendingmachine.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AddLocalBalanceDTO {
    private int tenCents;
    private int twentyCents;
    private int fiftyCents;
    private int oneDollar;
    private int twoDollars;
}
