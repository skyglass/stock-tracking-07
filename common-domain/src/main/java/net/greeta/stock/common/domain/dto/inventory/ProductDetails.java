package net.greeta.stock.common.domain.dto.inventory;

import lombok.Builder;

@Builder
public record ProductDetails(Integer productId,
                                  String description,
                                  Integer availableStock) {
}
