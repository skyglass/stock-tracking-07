package net.greeta.stock.order;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.customer.Customer;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.common.domain.dto.order.OrderStatus;
import net.greeta.stock.customer.CustomerTestHelper;
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
import java.util.UUID;
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

    @Autowired
    private CustomerTestHelper customerTestHelper;


    @Test
    @SneakyThrows
    void createParallelOrdersThenStockIsZeroTest() {
        int stockQuantity = 20;
        String customerName = "testCustomer";
        double customerBalance = 100.0;
        String productName = "testProduct";
        Integer orderQuantity = 2;
        double productPrice = 2.0;
        AtomicInteger counter = new AtomicInteger(0);
        Customer customer = customerTestHelper.createCustomer(customerName, customerBalance);
        Product product = inventoryTestHelper.createProduct(productName, stockQuantity);
        UUID customerId = customer.getId();
        UUID productId = product.getId();

        // Start the clock
        long start = Instant.now().toEpochMilli();

        int numberOfOrders = 20;
        List<CompletableFuture<UUID>> createdOrders = new ArrayList<>();

        for (int i = 0; i < numberOfOrders; i++) {
            CompletableFuture<UUID> orderSummary = orderTestHelper.asyncPlaceOrder(
                    productId, customerId, orderQuantity, productPrice, counter);
            createdOrders.add(orderSummary);
        }

        int numberOfStockUpdates = 10;
        List<CompletableFuture<Product>> addedStocks = new ArrayList<>();
        for (int i = 0; i < numberOfStockUpdates; i++) {
            CompletableFuture<Product> addStockResult = inventoryTestHelper
                    .asyncAddStock(productId, orderQuantity, counter);
            addedStocks.add(addStockResult);
        }

        // Wait until they are all done
        CompletableFuture.allOf(createdOrders.toArray(new CompletableFuture[0])).join();
        CompletableFuture.allOf(addedStocks.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<UUID> orderFuture: createdOrders) {
            UUID orderId = orderFuture.get();
            assertNotNull(orderId);
            log.info("--> " + orderId);
            Boolean orderCompleted =  RetryHelper.retry(() -> {
                var result = orderTestHelper.getOrder(orderId, counter);
                return Objects.equals(OrderStatus.COMPLETED, result.getStatus());
            });

            assertTrue(orderCompleted);
        }

        for (CompletableFuture<Product> addStockResultFuture: addedStocks) {
            Product addStockResult = addStockResultFuture.get();
            assertNotNull(addStockResult);
            log.info("Available Stock --> " + addStockResult.getStocks());
        }

        log.info("Elapsed time: " + (Instant.now().toEpochMilli() - start));

        Boolean stockReducedToZero =  RetryHelper.retry(() -> {
            Product productDetails = inventoryTestHelper.getProduct(productId, counter);
            return productDetails.getStocks() == 0;
        });

        assertTrue(stockReducedToZero);

        UUID notApprovedOrderId = orderTestHelper.placeOrder(productId, customerId, 1, productPrice, counter);

        Boolean orderStockNotApproved =  RetryHelper.retry(() -> {
            var result = orderTestHelper.getOrder(notApprovedOrderId, counter);
            return Objects.equals(OrderStatus.CANCELED, result.getStatus());
        });

        assertTrue(orderStockNotApproved);
    }


}

