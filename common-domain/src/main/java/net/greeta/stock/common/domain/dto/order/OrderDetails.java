package net.greeta.stock.common.domain.dto.order;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderDetails(PurchaseOrderDto order,
                           List<OrderWorkflowActionDto> actions) {
}
