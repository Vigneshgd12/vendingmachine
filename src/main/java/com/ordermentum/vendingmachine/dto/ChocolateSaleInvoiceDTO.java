package com.ordermentum.vendingmachine.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChocolateSaleInvoiceDTO {
    private String message;
    private ReturnChangeDTO change;
}
