package net.greeta.stock.common.domain.dto.order;

import lombok.Builder;

@Builder
public record OrderCreateRequest(Integer customerId,
                                 Integer productId,
                                 Integer quantity,
                                 Integer unitPrice) {
}
