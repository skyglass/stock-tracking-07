package net.greeta.stock.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.customer.Customer;
import net.greeta.stock.common.domain.dto.customer.CustomerRequest;
import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.common.domain.dto.inventory.ProductRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryTestHelper {

    private final InventoryClient inventoryClient;

    private final InventoryClient2 inventoryClient2;

    private final InventoryClient3 inventoryClient3;

    public Product createProduct(String name, int stocks) {
        ProductRequest productRequest = ProductRequest.builder()
                .name(name)
                .stocks(stocks)
                .build();
        return inventoryClient.create(productRequest);
    }

    public Product getProduct(UUID productId, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
                ? inventoryClient.findById(productId)
                : (hash % 3 == 1
                ? inventoryClient2.findById(productId)
                : inventoryClient3.findById(productId)
        );
    }

    @Async
    public CompletableFuture<Product> asyncAddStock(UUID productId, Integer quantity, AtomicInteger counter) {
        return CompletableFuture.completedFuture(addStock(productId, quantity, counter));
    }

    public Product addStock(UUID productId, Integer quantity, AtomicInteger counter) {
        AddStockRequest request = AddStockRequest
                .builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        return addStock(request, counter);
    }

    private Product addStock(AddStockRequest request, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
            ? inventoryClient.addStock(request)
            : (hash % 3 == 1
                ? inventoryClient2.addStock(request)
                : inventoryClient3.addStock(request)
            );
    }



}
