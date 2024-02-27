package net.greeta.stock.order;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order")
public interface OrderClient {

    @PostMapping("/")
    UUID placeOrder(@RequestBody @Valid OrderRequest orderRequest);

    @GetMapping("/{orderId}")
    Order getOrder(@PathVariable UUID orderId);

}
