package com.ordermentum.vendingmachine.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendingMachine {
    @Id
    private String id;
    @NotNull
    private Address machineAddress;
    private List<ChocolateDetail> chocolateDetails;
    private CoinsInStock coinsInStock;
    private LocalBalance localBalance;
}
