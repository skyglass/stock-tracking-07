package net.greeta.stock.common.domain.dto.inventory;

import lombok.Builder;

@Builder
public record ProductInventoryDto(Integer productId,
                                Integer availableStock,
                                InventoryStatus status) {
}
