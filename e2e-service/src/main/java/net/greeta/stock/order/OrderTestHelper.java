package net.greeta.stock.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.order.OrderCreateRequest;
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
public class OrderTestHelper {

    private final OrderClient orderClient;

    private final OrderClient2 orderClient2;

    private final OrderClient3 orderClient3;

    @Async
    public CompletableFuture<PurchaseOrderDto> asyncPlaceOrder(Integer productId, Integer quantity, AtomicInteger counter) {
        return CompletableFuture.completedFuture(placeOrder(productId, quantity, counter));
    }

    public PurchaseOrderDto placeOrder(Integer productId, Integer quantity, AtomicInteger counter) {
        OrderCreateRequest request = OrderCreateRequest
                .builder()
                .productId(productId)
                .customerId(1)
                .unitPrice(1)
                .quantity(quantity)
                .build();
        return placeOrder(request, counter);
    }

    public OrderDetails getOrderDetails(UUID orderId, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
                ? orderClient.getOrderDetails(orderId)
                : (hash % 3 == 1
                    ? orderClient2.getOrderDetails(orderId)
                    : orderClient3.getOrderDetails(orderId)
                );
    }

    private PurchaseOrderDto placeOrder(OrderCreateRequest request, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
            ? orderClient.placeOrder(request)
            : (hash % 3 == 1
                ? orderClient2.placeOrder(request)
                : orderClient3.placeOrder(request)
            );
    }



}
