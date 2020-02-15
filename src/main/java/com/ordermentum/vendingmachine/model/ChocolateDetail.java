package com.ordermentum.vendingmachine.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ChocolateDetail {

    @Id
    private String id;
    @NotNull
    private String chocolateName;
    @NotNull
    private double priceOfEach;
    @Min(0)
    @NotNull
    private int currentStock;
    @Min(0)
    @NotNull
    private double totalCurrentValue;
}
