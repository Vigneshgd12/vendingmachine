package com.ordermentum.vendingmachine.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address {
    @NotNull
    private String addressLine1;
    @NotNull
    private String addressLine2;
    @NotNull
    private String suburb;
    @NotNull
    private String state;
    @NotNull
    private int postcode;
    @NotNull
    private String country;
}
