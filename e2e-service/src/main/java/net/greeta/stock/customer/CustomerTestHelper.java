package net.greeta.stock.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.customer.Customer;
import net.greeta.stock.common.domain.dto.customer.CustomerRequest;
import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.common.domain.dto.inventory.ProductRequest;
import net.greeta.stock.inventory.InventoryClient;
import net.greeta.stock.inventory.InventoryClient2;
import net.greeta.stock.inventory.InventoryClient3;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerTestHelper {

    private final CustomerClient customerClient;

    public Customer createCustomer(String username, double balance) {
        CustomerRequest customer = CustomerRequest.builder()
                .username(username)
                .fullName(username)
                .balance(BigDecimal.valueOf(balance))
                .build();
        return customerClient.create(customer);
    }



}

