package com.ordermentum.vendingmachine.delegates;


import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.model.*;
import com.ordermentum.vendingmachine.repositories.VendingMachineRepository;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import com.ordermentum.vendingmachine.services.impl.VendingMachineServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
public class VendingDelegateTest {

    private VendingDelegate delegate;
    private VendingMachineService service;

    public VendingDelegateTest(){
        service = mock(VendingMachineServiceImpl.class);
        delegate = new VendingDelegate(service);
    }

    //@Test
    public void addLocalBalanceTest(){
        String id="testMachineId";
        when(service.getVendingMachineById(id)).thenReturn(buildVendingMachineObject());
        AddLocalBalanceDTO addLocalBalanceDTO = AddLocalBalanceDTO.builder().fiftyCents(1).oneDollar(1).twoDollars(1).build();
        delegate.addLocalBalance(id,addLocalBalanceDTO);
    }

    private VendingMachine buildVendingMachineObject(){
        return VendingMachine.builder()
                .chocolateDetails(Arrays.asList(ChocolateDetail.builder()
                        .chocolateName("Test")
                        .currentStock(10)
                        .priceOfEach(3.1)
                        .totalCurrentValue(31)
                        .id("testChocolateId")
                        .build()))
                .coinsInStock(CoinsInStock.builder()
                        .fiftyCents(10)
                        .oneDollar(10)
                        .tenCents(10)
                        .twentyCents(10)
                        .twoDollars(10)
                        .build())
                .id("testMachineId")
                .localBalance(LocalBalance.builder().build())
                .machineAddress(Address.builder().build())
                .build();
    }

}
