package net.greeta.stock.order;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.inventory.ProductDetails;
import net.greeta.stock.common.domain.dto.inventory.ProductInventoryDto;
import net.greeta.stock.common.domain.dto.order.OrderStatus;
import net.greeta.stock.common.domain.dto.order.PurchaseOrderDto;
import net.greeta.stock.helper.RetryHelper;
import net.greeta.stock.inventory.InventoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class OrderProcessingConcurrencyE2eTest extends E2eTest {

    @Autowired
    private InventoryTestHelper inventoryTestHelper;

    @Autowired
    private OrderTestHelper orderTestHelper;


    @Test
    @SneakyThrows
    void createParallelOrdersThenStockIsZeroTest() {
        Integer productId = 1;
        Integer productQuantity = 2;
        AtomicInteger counter = new AtomicInteger(0);

        // Start the clock
        long start = Instant.now().toEpochMilli();

        int numberOfOrders = 15;
        List<CompletableFuture<PurchaseOrderDto>> createdOrders = new ArrayList<>();

        for (int i = 0; i < numberOfOrders; i++) {
            CompletableFuture<PurchaseOrderDto> orderSummary = orderTestHelper.asyncPlaceOrder(
                    productId, productQuantity, counter);
            createdOrders.add(orderSummary);
        }

        int numberOfStockUpdates = 5;
        List<CompletableFuture<ProductInventoryDto>> addedStocks = new ArrayList<>();
        for (int i = 0; i < numberOfStockUpdates; i++) {
            CompletableFuture<ProductInventoryDto> addStockResult = inventoryTestHelper
                    .asyncAddStock(productId, productQuantity, counter);
            addedStocks.add(addStockResult);
        }

        // Wait until they are all done
        CompletableFuture.allOf(createdOrders.toArray(new CompletableFuture[0])).join();
        CompletableFuture.allOf(addedStocks.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<PurchaseOrderDto> orderFuture: createdOrders) {
            PurchaseOrderDto orderDto = orderFuture.get();
            assertNotNull(orderDto.orderId());
            log.info("--> " + orderDto.orderId());
            Boolean orderCompleted =  RetryHelper.retry(() -> {
                var result = orderTestHelper.getOrderDetails(orderDto.orderId(), counter);
                return Objects.equals(OrderStatus.COMPLETED, result.order().status());
            });

            assertTrue(orderCompleted);
        }

        for (CompletableFuture<ProductInventoryDto> addStockResultFuture: addedStocks) {
            ProductInventoryDto addStockResult = addStockResultFuture.get();
            assertNotNull(addStockResult);
            log.info("Available Stock --> " + addStockResult.availableStock());
        }

        log.info("Elapsed time: " + (Instant.now().toEpochMilli() - start));

        Boolean stockReducedToZero =  RetryHelper.retry(() -> {
            ProductDetails productDetails = inventoryTestHelper.getProductDetails(productId, counter);
            return productDetails.availableStock() == 0;
        });

        assertTrue(stockReducedToZero);

        //simulate long waiting for stock update
        TimeUnit.MILLISECONDS.sleep(Duration.ofSeconds(3).toMillis());

        PurchaseOrderDto notApprovedOrder = orderTestHelper.placeOrder(productId, 1, counter);

        Boolean orderStockNotApproved =  RetryHelper.retry(() -> {
            var result = orderTestHelper.getOrderDetails(notApprovedOrder.orderId(), counter);
            return Objects.equals(OrderStatus.CANCELLED, result.order().status());
        });

        assertTrue(orderStockNotApproved);
    }


}

