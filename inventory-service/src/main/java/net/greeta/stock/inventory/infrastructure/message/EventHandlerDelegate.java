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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandlerDelegate {

    private final ObjectMapper mapper;

    private final ProductUseCasePort productUseCase;

    private final MessageLogRepository messageLogRepository;

    private final OutBoxRepository outBoxRepository;

    private static final String PRODUCT = "PRODUCT";

    private static final String RESERVE_CUSTOMER_BALANCE_SUCCESSFULLY = "RESERVE_CUSTOMER_BALANCE_SUCCESSFULLY";

    private static final String RESERVE_PRODUCT_STOCK_FAILED = "RESERVE_PRODUCT_STOCK_FAILED";

    private static final String RESERVE_PRODUCT_STOCK_SUCCESSFULLY = "RESERVE_PRODUCT_STOCK_SUCCESSFULLY";

    @Transactional
    public void handleReserveProductStockRequest(Message<String> event) {
        var messageId = event.getHeaders().getId();
        log.debug("EventHandlerAdapter.handleReserveProductStockRequest: Started processing message {}", messageId);
        if (Objects.nonNull(messageId) && !messageLogRepository.isMessageProcessed(messageId)) {
            var eventType = getHeaderAsString(event.getHeaders(), "eventType");
            if (eventType.equals(RESERVE_CUSTOMER_BALANCE_SUCCESSFULLY)) {
                var placedOrderEvent = deserialize(event.getPayload());

                log.debug("Start process reserve product stock {}", placedOrderEvent);
                var outbox = new OutBox();
                outbox.setAggregateId(placedOrderEvent.id());
                outbox.setAggregateType(PRODUCT);
                outbox.setPayload(mapper.convertValue(placedOrderEvent, JsonNode.class));

                if (productUseCase.reserveProduct(placedOrderEvent)) {
                    outbox.setType(RESERVE_PRODUCT_STOCK_SUCCESSFULLY);
                } else {
                    outbox.setType(RESERVE_PRODUCT_STOCK_FAILED);
                }

                outBoxRepository.save(outbox);
                log.debug("Done process reserve product stock {}", placedOrderEvent);
            }

            messageLogRepository.save(new MessageLog(messageId, Timestamp.from(Instant.now())));
        }
    }

    @Transactional
    public void handleDlq(Message<String> event) {
        var messageId = event.getHeaders().getId();
        log.debug("EventHandlerAdapter.handleReserveProductStockRequest: Started processing message {}", messageId);
        if (Objects.nonNull(messageId) && !messageLogRepository.isMessageProcessed(messageId)) {
            var placedOrderEvent = deserialize(event.getPayload());

            log.debug("Start failed process reserve product stock event {}", placedOrderEvent);
            var outbox = new OutBox();
            outbox.setAggregateId(placedOrderEvent.id());
            outbox.setAggregateType(PRODUCT);
            outbox.setPayload(mapper.convertValue(placedOrderEvent, JsonNode.class));
            outbox.setType(RESERVE_PRODUCT_STOCK_FAILED);

            outBoxRepository.save(outbox);
            log.debug("Done failed process reserve product stock {}", placedOrderEvent);

            messageLogRepository.save(new MessageLog(messageId, Timestamp.from(Instant.now())));
        }
    }

    private PlacedOrderEvent deserialize(String event) {
        PlacedOrderEvent placedOrderEvent;
        try {
            placedOrderEvent = mapper.readValue(event, PlacedOrderEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't deserialize event", e);
        }
        return placedOrderEvent;
    }

    private String getHeaderAsString(MessageHeaders headers, String name) {
        var value = headers.get(name, byte[].class);
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(
                    String.format("Expected record header %s not present", name));
        }
        return new String(value, StandardCharsets.UTF_8);
    }
}
