package com.ordermentum.vendingmachine.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CoinsInStock {

    @NotNull
    @Min(0)
    private int tenCents;
    @NotNull
    @Min(0)
    private int twentyCents;
    @NotNull
    @Min(0)
    private int fiftyCents;
    @NotNull
    @Min(0)
    private int oneDollar;
    @NotNull
    @Min(0)
    private int twoDollars;


}
