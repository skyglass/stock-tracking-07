package net.greeta.stock.order;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order2")
public interface OrderClient2 {

    @PostMapping("/")
    UUID placeOrder(@RequestBody @Valid OrderRequest orderRequest);

    @GetMapping("/{orderId}")
    Order getOrder(@PathVariable UUID orderId);

}
