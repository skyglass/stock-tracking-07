package net.greeta.stock.inventory.infrastructure.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.inventory.domain.PlacedOrderEvent;
import net.greeta.stock.inventory.domain.exception.NotEnoughStockException;
import net.greeta.stock.inventory.domain.port.EventHandlerPort;
import net.greeta.stock.inventory.domain.port.ProductUseCasePort;
import net.greeta.stock.inventory.infrastructure.message.log.MessageLog;
import net.greeta.stock.inventory.infrastructure.message.log.MessageLogRepository;
import net.greeta.stock.inventory.infrastructure.message.outbox.OutBox;
import net.greeta.stock.inventory.infrastructure.message.outbox.OutBoxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventHandlerAdapter implements EventHandlerPort {

  private final EventHandlerDelegate eventHandlerDelegate;

  @Bean
  @Override
  @Transactional
  public Consumer<Message<String>> handleReserveProductStockRequest() {
    return event -> eventHandlerDelegate.handleReserveProductStockRequest(event);
  }

  @Bean
  @Override
  @Transactional
  public Consumer<Message<String>> handleDlq() {
    return event -> eventHandlerDelegate.handleDlq(event);
  }
}
