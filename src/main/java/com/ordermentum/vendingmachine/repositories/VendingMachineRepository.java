package com.ordermentum.vendingmachine.repositories;

import com.ordermentum.vendingmachine.model.VendingMachine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingMachineRepository extends MongoRepository<VendingMachine, String> {
}
