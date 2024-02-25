package net.greeta.stock.order.domain.port;

import net.greeta.stock.common.domain.dto.order.OrderRequest;
import net.greeta.stock.common.domain.dto.order.Order;

import java.util.UUID;

public interface OrderUseCasePort {

  UUID placeOrder(OrderRequest orderRequest);

  void updateOrderStatus(UUID orderId, boolean success);

  Order getOrder(UUID orderId);

}
