package net.greeta.stock.common.domain.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ProductRequest(@NotBlank String name, int stocks) {

}
