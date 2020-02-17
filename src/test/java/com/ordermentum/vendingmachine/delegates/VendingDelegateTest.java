package com.ordermentum.vendingmachine.delegates;



import com.ordermentum.vendingmachine.dto.AddLocalBalanceDTO;
import com.ordermentum.vendingmachine.dto.ChocolateSaleInvoiceDTO;
import com.ordermentum.vendingmachine.exception.LowBalanceException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void sellChocolateWithNoLocalBalanceTest(){
        VendingMachine machine = buildVendingMachineObject();
        when(service.getVendingMachineById("testMachineId")).thenReturn(machine);
        Assertions.assertThrows(LowBalanceException.class, ()->delegate.sellChocolate("testMachineId", "testChocolateId2"));
    }

    @Test
    public void sellOutOfStockChocolateTest(){
        VendingMachine machine = buildVendingMachineObjectWithLocalBalance();
        when(service.getVendingMachineById("testMachineId")).thenReturn(machine);
        ChocolateSaleInvoiceDTO invoice = delegate.sellChocolate("testMachineId", "testChocolateId3");
        Assert.assertEquals(invoice.getChange().getTotalChangeReturned(),4.0,0.01);
    }

    @Test
    public void sellChocolateWithInsufficientBalanceTest(){
        VendingMachine machine = buildVendingMachineObjectWithNoCoinsInStock();
        when(service.getVendingMachineById("testMachineId")).thenReturn(machine);
        ChocolateSaleInvoiceDTO invoice = delegate.sellChocolate("testMachineId", "testChocolateId3");
        Assert.assertEquals(invoice.getChange().getTotalChangeReturned(),4.0,0.01);
    }

    @Test
    public void sellChocolateTest(){
        VendingMachine machine = buildVendingMachineObjectWithLocalBalance();
        when(service.getVendingMachineById("testMachineId")).thenReturn(machine);
        ChocolateSaleInvoiceDTO invoice = delegate.sellChocolate("testMachineId", "testChocolateId2");
        Assert.assertEquals(invoice.getChange().getTotalChangeReturned(),1.5,0.01);
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
                .chocolateDetails(getChocolateDetails())
                .coinsInStock(getCoinsInStock())
                .id("testMachineId")
                .localBalance(LocalBalance.builder().build())
                .machineAddress(Address.builder().build())
                .build();
    }

    private VendingMachine buildVendingMachineObjectWithNoCoinsInStock(){
        return VendingMachine.builder()
                .chocolateDetails(getChocolateDetails())
                .coinsInStock(CoinsInStock.builder().build())
                .id("testMachineId")
                .localBalance(LocalBalance.builder().oneDollar(4).localBalanceValue(4.0).build())
                .machineAddress(Address.builder().build())
                .build();
    }

    private VendingMachine buildVendingMachineObjectWithLocalBalance(){
        return VendingMachine.builder()
                .chocolateDetails(getChocolateDetails())
                .coinsInStock(getCoinsInStock())
                .id("testMachineId")
                .localBalance(LocalBalance.builder().oneDollar(4).localBalanceValue(4.0).build())
                .machineAddress(Address.builder().build())
                .build();
    }


    private CoinsInStock getCoinsInStock() {
        return CoinsInStock.builder()
                .fiftyCents(10)
                .oneDollar(10)
                .tenCents(10)
                .twentyCents(10)
                .twoDollars(10)
                .build();
    }

    private List<ChocolateDetail> getChocolateDetails() {
        List<ChocolateDetail> chocolateDetails = new ArrayList<>();
        chocolateDetails.add(ChocolateDetail.builder().chocolateName("Test1").currentStock(10).priceOfEach(3.1).totalCurrentValue(31).id("testChocolateId1").build());
        chocolateDetails.add(ChocolateDetail.builder().chocolateName("Test2").currentStock(10).priceOfEach(2.5).totalCurrentValue(35).id("testChocolateId2").build());
        chocolateDetails.add(ChocolateDetail.builder().chocolateName("outOfStock").currentStock(0).priceOfEach(3.1).totalCurrentValue(0).id("testChocolateId3").build());
        chocolateDetails.add(ChocolateDetail.builder().chocolateName("Test4").currentStock(10).priceOfEach(5.5).totalCurrentValue(55).id("testChocolateId4").build());

        return chocolateDetails;
    }

}
