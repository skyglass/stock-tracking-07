package net.greeta.stock.order.domain.port;

import net.greeta.stock.order.domain.OrderRequest;

import java.util.UUID;

public interface OrderUseCasePort {

  void placeOrder(OrderRequest orderRequest);

  void updateOrderStatus(UUID orderId, boolean success);
}
