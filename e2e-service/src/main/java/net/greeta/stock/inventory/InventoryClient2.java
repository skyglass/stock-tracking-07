package net.greeta.stock.inventory;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.common.domain.dto.inventory.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "inventory2")
public interface InventoryClient2 {

    @PostMapping("/")
    Product create(@RequestBody @Valid ProductRequest productRequest);

    @GetMapping("/{productId}")
    Product findById(@PathVariable UUID productId);

    @PostMapping("/add-stock")
    public Product addStock(@RequestBody @Valid AddStockRequest addStockRequest);
}
