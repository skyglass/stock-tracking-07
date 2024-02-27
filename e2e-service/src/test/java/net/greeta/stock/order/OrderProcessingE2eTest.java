package net.greeta.stock.order;

import lombok.SneakyThrows;
import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.customer.Customer;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.customer.CustomerTestHelper;
import net.greeta.stock.helper.RetryHelper;
import net.greeta.stock.inventory.InventoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderProcessingE2eTest extends E2eTest {

    @Autowired
    private InventoryTestHelper inventoryTestHelper;

    @Autowired
    private OrderTestHelper orderTestHelper;

    @Autowired
    private CustomerTestHelper customerTestHelper;


    @Test
    @SneakyThrows
    void test() {
        Integer stockQuantity = 20;
        String customerName = "testCustomer";
        double customerBalance = 100.0;
        String productName = "testProduct";
        Integer orderQuantity = 2;
        double productPrice = 2.0;
        AtomicInteger counter = new AtomicInteger(0);

        Customer customer = customerTestHelper.createCustomer(customerName, customerBalance);
        Product product = inventoryTestHelper.createProduct(productName, stockQuantity);

        UUID orderId = orderTestHelper.placeOrder(product.getId(), customer.getId(),
                orderQuantity, productPrice, counter);

        Order orderCreated =  RetryHelper.retry(() ->
            orderTestHelper.getOrder(orderId, counter)
        );

        assertNotNull(orderCreated);

        assertEquals(orderId, orderCreated.getId());
        assertEquals(product.getId(), orderCreated.getProductId());
        assertEquals(orderQuantity, orderCreated.getQuantity());

        Boolean stockReduced =  RetryHelper.retry(() -> {
            Product p = inventoryTestHelper.getProduct(product.getId(), counter);
            return p.getStocks() == stockQuantity - orderQuantity;
        });

        assertTrue(stockReduced);

        inventoryTestHelper.addStock(product.getId(), 3, counter);

        Boolean stockIncreased =  RetryHelper.retry(() -> {
            Product p = inventoryTestHelper.getProduct(product.getId(), counter);
            return p.getStocks() == stockQuantity - orderQuantity + 3;
        });

        assertTrue(stockIncreased);
    }


}
