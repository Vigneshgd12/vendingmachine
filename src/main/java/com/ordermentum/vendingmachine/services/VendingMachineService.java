package com.ordermentum.vendingmachine.services;

import com.ordermentum.vendingmachine.dto.LoadVendingMachineDTO;
import com.ordermentum.vendingmachine.model.VendingMachine;

public interface VendingMachineService {

    VendingMachine getVendingMachineById(String id);

    String addNewVendingMachine(VendingMachine vendingMachine);

    VendingMachine load(String id, LoadVendingMachineDTO vendingMachine);

    VendingMachine updateVendingMachine(VendingMachine machine);
}
