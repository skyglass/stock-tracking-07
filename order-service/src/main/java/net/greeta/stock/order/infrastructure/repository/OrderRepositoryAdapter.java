package net.greeta.stock.order.infrastructure.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.order.domain.port.OrderRepositoryPort;
import net.greeta.stock.order.infrastructure.message.outbox.OutBox;
import net.greeta.stock.order.infrastructure.message.outbox.OutBoxRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.greeta.stock.order.infrastructure.message.EventHandlerAdapter;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

  private final ObjectMapper mapper;

  private final OrderJpaRepository orderJpaRepository;

  private final OutBoxRepository outBoxRepository;

  @Override
  public Optional<Order> findOrderById(UUID orderId) {
    return orderJpaRepository
        .findById(orderId)
        .map(orderEntity -> mapper.convertValue(orderEntity, Order.class));
  }

  @Override
  public void saveOrder(Order order) {
    var entity = mapper.convertValue(order, OrderEntity.class);
    orderJpaRepository.save(entity);
  }

  @Override
  public void exportOutBoxEvent(Order order) {
    var outbox =
        OutBox.builder()
            .aggregateId(order.getId())
            .aggregateType(EventHandlerAdapter.ORDER)
            .type(EventHandlerAdapter.ORDER_CREATED)
            .payload(mapper.convertValue(order, JsonNode.class))
            .build();
    outBoxRepository.save(outbox);
  }
}
