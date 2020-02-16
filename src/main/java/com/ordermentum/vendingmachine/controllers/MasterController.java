package com.ordermentum.vendingmachine.controllers;

import com.ordermentum.vendingmachine.dto.LoadVendingMachineDTO;
import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vending-machine")
public class MasterController {

    @Autowired
    private VendingMachineService service;


    @PostMapping
    public String addNewVendingMachine(@RequestBody  VendingMachine vendingMachine){
        return service.addNewVendingMachine(vendingMachine);
    }

    @GetMapping
    public List<VendingMachine> getAllVendingMachines(){
        return service.getAllVendingMachines();
    }

    @GetMapping("/{id}")
    public VendingMachine getVendingMachineById(@PathVariable  String vendingMachineId){
        return service.getVendingMachineById(vendingMachineId);
    }

    @PostMapping("/{id}/load")
    public VendingMachine fullLoad(@PathVariable String id,  @RequestBody LoadVendingMachineDTO loadVendingMachineDTO){
        return service.fullLoad(id,loadVendingMachineDTO);
    }

    @PatchMapping("/{id}/load")
    public VendingMachine partialLoad(@PathVariable String id,  @RequestBody LoadVendingMachineDTO loadVendingMachineDTO){
        return service.partialLoad(id,loadVendingMachineDTO);
    }

}
