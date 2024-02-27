package net.greeta.stock.inventory.domain;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.inventory.domain.port.ProductUseCasePort;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRetryHelper {

    private final ProductUseCasePort productUseCase;

    @Retryable(retryFor = {DataAccessException.class},
            maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public Product addStock(AddStockRequest addStockRequest) {
        return productUseCase.addStock(addStockRequest);
    }

}
