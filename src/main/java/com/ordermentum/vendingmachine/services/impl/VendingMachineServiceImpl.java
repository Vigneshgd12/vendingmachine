package com.ordermentum.vendingmachine.services.impl;

import com.ordermentum.vendingmachine.dto.LoadVendingMachineDTO;
import com.ordermentum.vendingmachine.exception.InvalidRequestException;
import com.ordermentum.vendingmachine.model.ChocolateDetail;
import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.repositories.VendingMachineRepository;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.ordermentum.vendingmachine.helper.TransactionHelper.*;
@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    @Autowired
    private VendingMachineRepository repository;


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
        machine.getChocolateDetails().stream().forEach(
                chocolate -> chocolate.setId(Integer.toHexString(new Random().nextInt())));
        machine.setCoinsInStock(vendingMachine.getCoinsInStock());
        return repository.save(machine);
    }


    @Override
    public VendingMachine partialLoad(String id, LoadVendingMachineDTO vendingMachine) {
        VendingMachine machine = repository.findById(id).orElseThrow(()->new InvalidRequestException());
        if(!CollectionUtils.isEmpty(vendingMachine.getChocolateDetails())) {
            loadChocolates(vendingMachine.getChocolateDetails(), machine);
        }
        if(null != vendingMachine.getCoinsInStock())
            addCoinsToStock(machine,vendingMachine.getCoinsInStock());
        return repository.save(machine);
    }

    private void loadChocolates(List<ChocolateDetail> chocolateDetails, VendingMachine machine) {
        chocolateDetails.stream()
                .filter(chocolate->null == chocolate.getId())
                .forEach(chocolate -> chocolate.setId(Integer.toHexString(new Random().nextInt())));
        List<String> chocolateNames = chocolateDetails.stream()
                .map(chocolateDetail -> chocolateDetail.getChocolateName())
                .collect(Collectors.toList());
        machine.getChocolateDetails().removeIf(chocolateDetail -> chocolateNames.contains(chocolateDetail.getId()));
        machine.getChocolateDetails().addAll(chocolateDetails);
    }

    private ChocolateDetail addChocolates(ChocolateDetail existingchocolate, ChocolateDetail incomingChocolate){
        return ChocolateDetail.builder()
                .id(existingchocolate.getId())
                .chocolateName(existingchocolate.getChocolateName())
                .totalCurrentValue(existingchocolate.getTotalCurrentValue() + incomingChocolate.getTotalCurrentValue())
                .priceOfEach(existingchocolate.getPriceOfEach())
                .currentStock(existingchocolate.getCurrentStock()+incomingChocolate.getCurrentStock())
                .build();
    }


    @Override
    public VendingMachine updateVendingMachine(VendingMachine machine){
        return repository.save(machine);
    }

    @Override
    public List<VendingMachine> getAllVendingMachines() {
        return repository.findAll();
    }
}
