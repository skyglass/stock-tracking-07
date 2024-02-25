package net.greeta.stock.common.domain.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CustomerRequest(@NotBlank String username, @NotBlank String fullName,
                              @NotNull BigDecimal balance) {

}
