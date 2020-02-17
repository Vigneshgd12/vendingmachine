package com.ordermentum.vendingmachine.delegates;



import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.exception.MaximumInputAmountExceededException;
import com.ordermentum.vendingmachine.model.*;
import com.ordermentum.vendingmachine.repositories.VendingMachineRepository;
import com.ordermentum.vendingmachine.services.VendingMachineService;
import com.ordermentum.vendingmachine.services.impl.VendingMachineServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import static com.ordermentum.vendingmachine.constants.VendingMachineConstants.*;
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

    @Test
    public void addLocalBalanceTest(){
        String id="testMachineId";
        VendingMachine machine = buildVendingMachineObject();
        when(service.getVendingMachineById(id)).thenReturn(machine);
        AddLocalBalanceDTO addLocalBalanceDTO = AddLocalBalanceDTO.builder().fiftyCents(1).oneDollar(1).twoDollars(1).build();
        delegate.addLocalBalance(id,addLocalBalanceDTO);
        Assert.assertEquals(machine.getLocalBalance().getLocalBalanceValue(), calculateAddedLocalBalanceTotalValue(addLocalBalanceDTO),0.01);
    }


    @Test
    public void addMoreThanMaximumLocalBalanceTest(){
        String id="testMachineId";
        VendingMachine machine = buildVendingMachineObject();
        when(service.getVendingMachineById(id)).thenReturn(machine);
        AddLocalBalanceDTO addLocalBalanceDTO = AddLocalBalanceDTO.builder().fiftyCents(1).oneDollar(1).twoDollars(10).build();
        Assertions.assertThrows(MaximumInputAmountExceededException.class, ()->delegate.addLocalBalance(id,addLocalBalanceDTO));
    }


    private double calculateAddedLocalBalanceTotalValue(AddLocalBalanceDTO balance){
        return balance.getTenCents()* TEN_CENTS_VALUE +
                balance.getTwentyCents()* TWENTY_CENTS_VALUE +
                balance.getFiftyCents()* FIFTY_CENTS_VALUE +
                balance.getOneDollar()* ONE_DOLLAR_VALUE +
                balance.getTwoDollars()* TWO_DOLLARS_VALUE;
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
