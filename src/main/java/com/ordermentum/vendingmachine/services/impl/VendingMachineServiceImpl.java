package com.ordermentum.vendingmachine.services.impl;

import com.ordermentum.vendingmachine.dto.LoadVendingMachineDTO;
import com.ordermentum.vendingmachine.exception.InvalidRequestException;
import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.repositories.impl.VendingMachineRepositoryImpl;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.ordermentum.vendingmachine.helper.TransactionHelper.*;
@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    @Autowired
    private VendingMachineRepositoryImpl repository;


    @Override
    public VendingMachine getVendingMachineById(String id){
        return repository.findById(id).orElseThrow(()->new InvalidRequestException());
    }

    @Override
    public String addNewVendingMachine(VendingMachine vendingMachine) {
        return repository.save(vendingMachine).getId();
    }

    @Override
    public VendingMachine fullLoad(String id, LoadVendingMachineDTO vendingMachine) {
        VendingMachine machine = repository.findById(id).orElseThrow(()->new InvalidRequestException());
        machine.setChocolateDetails(vendingMachine.getChocolateDetails());
        machine.setCoinsInStock(vendingMachine.getCoinsInStock());
        return repository.save(machine);
    }


    @Override
    public VendingMachine partialLoad(String id, LoadVendingMachineDTO vendingMachine) {
        VendingMachine machine = repository.findById(id).orElseThrow(()->new InvalidRequestException());
        if(!CollectionUtils.isEmpty(vendingMachine.getChocolateDetails())) {
            List<String> chocolateNames = vendingMachine.getChocolateDetails().stream()
                    .map(chocolateDetail -> chocolateDetail.getChocolateName()).collect(Collectors.toList());
            machine.getChocolateDetails().removeIf(chocolateDetail -> chocolateNames.contains(chocolateDetail.getId()));
            machine.getChocolateDetails().addAll(vendingMachine.getChocolateDetails());
        }
        if(null != vendingMachine.getCoinsInStock())
            addCoinsToStock(machine,vendingMachine.getCoinsInStock());
        return repository.save(machine);
    }



    @Override
    public VendingMachine updateVendingMachine(VendingMachine machine){
        return repository.save(machine);
    }
}
