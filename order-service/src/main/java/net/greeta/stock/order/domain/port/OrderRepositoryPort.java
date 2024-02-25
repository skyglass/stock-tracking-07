package net.greeta.stock.order.domain.port;

import net.greeta.stock.common.domain.dto.order.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {

  Optional<Order> findOrderById(UUID orderId);

  void saveOrder(Order order);

  void exportOutBoxEvent(Order order);
}
