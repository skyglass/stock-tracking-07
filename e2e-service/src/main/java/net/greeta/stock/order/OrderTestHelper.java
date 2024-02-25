package net.greeta.stock.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.OrderRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    public CompletableFuture<UUID> asyncPlaceOrder(UUID productId, UUID customerId,
                                                   Integer quantity,
                                                   double price,
                                                   AtomicInteger counter) {
        return CompletableFuture.completedFuture(placeOrder(productId, customerId,
                quantity, price, counter));
    }

    public UUID placeOrder(UUID productId, UUID customerId,
                           Integer quantity,
                           double price,
                           AtomicInteger counter) {
        OrderRequest request = OrderRequest
                .builder()
                .productId(productId)
                .customerId(customerId)
                .quantity(quantity)
                .price(BigDecimal.valueOf(price))
                .build();
        return placeOrder(request, counter);
    }

    public Order getOrder(UUID orderId, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
                ? orderClient.getOrder(orderId)
                : (hash % 3 == 1
                    ? orderClient2.getOrder(orderId)
                    : orderClient3.getOrder(orderId)
                );
    }

    private UUID placeOrder(OrderRequest request, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 3 == 0
            ? orderClient.placeOrder(request)
            : (hash % 3 == 1
                ? orderClient2.placeOrder(request)
                : orderClient3.placeOrder(request)
            );
    }



}
