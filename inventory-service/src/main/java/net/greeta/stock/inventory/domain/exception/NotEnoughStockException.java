package net.greeta.stock.inventory.domain.exception;

import java.util.UUID;

public class NotEnoughStockException extends RuntimeException {

    private static final String MESSAGE = "Product %s does not have enough stock (current stock = %s, requested stock = %s)";

    public NotEnoughStockException(UUID productId, Integer stocks, Integer requestedStock) {
        super(MESSAGE.formatted(productId, stocks, requestedStock));
    }

}
