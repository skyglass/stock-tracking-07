package net.greeta.stock.common.domain.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderRequest(@NotNull UUID customerId,
                           @NotNull UUID productId,
                           @Min(1) @NotNull Integer quantity,
                           BigDecimal price) {

}
