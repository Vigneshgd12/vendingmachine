package com.ordermentum.vendingmachine.controllers;

import com.ordermentum.vendingmachine.dto.LoadVendingMachineDTO;
import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vending-machine")
public class MasterController {

    @Autowired
    private VendingMachineService service;


    @PostMapping
    public String addNewVendingMachine(@RequestBody  VendingMachine vendingMachine){
        return service.addNewVendingMachine(vendingMachine);
    }

    @PostMapping("/{id}/load")
    public VendingMachine load(@PathVariable String id,  @RequestBody LoadVendingMachineDTO loadVendingMachineDTO){
        return service.load(id,loadVendingMachineDTO);
    }

}
