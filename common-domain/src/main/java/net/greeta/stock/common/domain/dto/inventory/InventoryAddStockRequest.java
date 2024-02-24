package net.greeta.stock.common.domain.dto.inventory;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InventoryAddStockRequest(Integer productId,
                                     Integer quantity) {
}
