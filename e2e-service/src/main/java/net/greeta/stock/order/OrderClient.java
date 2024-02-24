package net.greeta.stock.order;

import net.greeta.stock.common.domain.dto.order.OrderCreateRequest;
import net.greeta.stock.common.domain.dto.order.OrderDetails;
import net.greeta.stock.common.domain.dto.order.PurchaseOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order")
public interface OrderClient {

    @PostMapping
    public PurchaseOrderDto placeOrder(@RequestBody OrderCreateRequest request);

    @GetMapping("all")
    public List<PurchaseOrderDto> getAllOrders();

    @GetMapping("{orderId}")
    public OrderDetails getOrderDetails(@PathVariable UUID orderId);

}
