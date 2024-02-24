package net.greeta.stock.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.inventory.InventoryAddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.ProductDetails;
import net.greeta.stock.common.domain.dto.inventory.ProductInventoryDto;
import net.greeta.stock.common.domain.dto.order.OrderDetails;
import net.greeta.stock.common.domain.dto.order.PurchaseOrderDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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

    public ProductDetails getProductDetails(Integer productId, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
                ? inventoryClient.getProductDetails(productId)
                : (hash % 3 == 1
                ? inventoryClient2.getProductDetails(productId)
                : inventoryClient3.getProductDetails(productId)
        );
    }

    @Async
    public CompletableFuture<ProductInventoryDto> asyncAddStock(Integer productId, Integer quantity, AtomicInteger counter) {
        return CompletableFuture.completedFuture(addStock(productId, quantity, counter));
    }

    public ProductInventoryDto addStock(Integer productId, Integer quantity, AtomicInteger counter) {
        InventoryAddStockRequest request = InventoryAddStockRequest
                .builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        return addStock(request, counter);
    }

    private ProductInventoryDto addStock(InventoryAddStockRequest request, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
            ? inventoryClient.addStock(request)
            : (hash % 3 == 1
                ? inventoryClient2.addStock(request)
                : inventoryClient3.addStock(request)
            );
    }



}
