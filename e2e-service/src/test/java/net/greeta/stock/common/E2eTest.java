package net.greeta.stock.common;

import lombok.SneakyThrows;
import net.greeta.stock.client.KafkaClient;
import net.greeta.stock.config.MockHelper;
import net.greeta.stock.customer.CustomerTestDataService;
import net.greeta.stock.inventory.InventoryTestDataService;
import net.greeta.stock.order.OrderTestDataService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class E2eTest {

    @Value("${security.oauth2.username}")
    private String securityOauth2Username;

    @Value("${security.oauth2.password}")
    private String securityOauth2Password;

    @Autowired
    private MockHelper mockHelper;

    @Autowired
    private CustomerTestDataService customerTestDataService;

    @Autowired
    private InventoryTestDataService inventoryTestDataService;

    @Autowired
    private OrderTestDataService orderTestDataService;

    @Autowired
    private KafkaClient kafkaClient;

    @BeforeEach
    @SneakyThrows
    void cleanup() {
        kafkaClient.clearMessages("CUSTOMER.events");
        kafkaClient.clearMessages("PRODUCT.events");
        kafkaClient.clearMessages("ORDER.events");
        kafkaClient.clearMessages("ORDER.events.dlq");
        mockHelper.mockCredentials(securityOauth2Username, securityOauth2Password);
        orderTestDataService.resetDatabase();
        inventoryTestDataService.resetDatabase();
        customerTestDataService.resetDatabase();
        //TimeUnit.MILLISECONDS.sleep(Duration.ofSeconds(1).toMillis());
    }
}
