package net.greeta.stock.order;

import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.inventory.ProductDetails;
import net.greeta.stock.common.domain.dto.order.OrderDetails;
import net.greeta.stock.common.domain.dto.order.PurchaseOrderDto;
import net.greeta.stock.helper.RetryHelper;
import net.greeta.stock.inventory.InventoryTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderProcessingE2eTest extends E2eTest {

    @Autowired
    private InventoryTestHelper inventoryTestHelper;

    @Autowired
    private OrderTestHelper orderTestHelper;


    @Test
    void test() {
        Integer stockQuantity = 20;
        Integer productId = 1;
        Integer productQuantity = 2;
        AtomicInteger counter = new AtomicInteger(0);

        PurchaseOrderDto orderDto = orderTestHelper.placeOrder(productId, productQuantity, counter);

        OrderDetails orderCreated =  RetryHelper.retry(() ->
            orderTestHelper.getOrderDetails(orderDto.orderId(), counter)
        );

        assertNotNull(orderCreated);

        assertEquals(orderDto.orderId(), orderCreated.order().orderId());
        assertEquals(productId, orderCreated.order().productId());
        assertEquals(orderDto.productId(), orderCreated.order().productId());
        assertEquals(productQuantity, orderCreated.order().quantity());
        assertEquals(orderDto.quantity(), orderCreated.order().quantity());

        Boolean stockReduced =  RetryHelper.retry(() -> {
            ProductDetails productDetails = inventoryTestHelper.getProductDetails(productId, counter);
            return productDetails.availableStock() == stockQuantity - productQuantity;
        });

        assertTrue(stockReduced);

        inventoryTestHelper.addStock(productId, 3, counter);

        Boolean stockIncreased =  RetryHelper.retry(() -> {
            ProductDetails productDetails = inventoryTestHelper.getProductDetails(productId, counter);
            return productDetails.availableStock() == stockQuantity - productQuantity + 3;
        });

        assertTrue(stockIncreased);
    }


}
