package com.ordermentum.vendingmachine.repositories.impl;

import com.ordermentum.vendingmachine.model.VendingMachine;
import com.ordermentum.vendingmachine.repositories.VendingMachineRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingMachineRepositoryImpl extends MongoRepository<VendingMachine, String>, VendingMachineRepository {
}
