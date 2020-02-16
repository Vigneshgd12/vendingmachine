package com.ordermentum.vendingmachine.services;

import com.ordermentum.vendingmachine.dto.LoadVendingMachineDTO;
import com.ordermentum.vendingmachine.model.VendingMachine;

import java.util.List;

public interface VendingMachineService {

    VendingMachine getVendingMachineById(String id);

    String addNewVendingMachine(VendingMachine vendingMachine);

    VendingMachine fullLoad(String id, LoadVendingMachineDTO vendingMachine);

    VendingMachine partialLoad(String id, LoadVendingMachineDTO vendingMachine);

    VendingMachine updateVendingMachine(VendingMachine machine);

    List<VendingMachine> getAllVendingMachines();
}
