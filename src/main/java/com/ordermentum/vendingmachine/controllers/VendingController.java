package com.ordermentum.vendingmachine.controllers;

import com.ordermentum.vendingmachine.delegates.VendingDelegate;
import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.dto.ChocolateSaleInvoiceDTO;
import com.ordermentum.vendingmachine.model.LocalBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vending-machine")
public class VendingController {

    @Autowired
    private VendingDelegate delegate;

    @PutMapping("/{id}/addLocalBalance")
    public void addLocalBalance(@PathVariable String id, @RequestBody  AddLocalBalanceDTO localBalance){
        delegate.addLocalBalance(id, localBalance);
    }

    @PatchMapping("/{id}/chocolate/{chocolateId}")
    public ChocolateSaleInvoiceDTO sellChocolate(@PathVariable String id, @PathVariable String chocolateId){
        return delegate.sellChocolate(id,chocolateId);
    }
}
