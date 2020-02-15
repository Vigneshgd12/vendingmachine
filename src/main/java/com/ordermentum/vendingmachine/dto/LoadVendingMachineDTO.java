package com.ordermentum.vendingmachine.dto;

import com.ordermentum.vendingmachine.model.ChocolateDetail;
import com.ordermentum.vendingmachine.model.CoinsInStock;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Data
@Getter
@Setter
public class LoadVendingMachineDTO {
    private List<ChocolateDetail> chocolateDetails;
    private CoinsInStock coinsInStock;
}
