package net.greeta.stock.order.infrastructure.repository;

import jakarta.persistence.*;
import net.greeta.stock.common.domain.dto.order.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

  @Id
  private UUID id;

  @Version
  private int version;

  @Column(nullable = false)
  private UUID customerId;

  @Column(nullable = false)
  private UUID productId;

  private BigDecimal price;

  private int quantity;

  private Timestamp createdAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
