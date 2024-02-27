package net.greeta.stock.inventory;

import jakarta.validation.Valid;
import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.common.domain.dto.inventory.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "inventory")
public interface InventoryClient {

    @PostMapping("/")
    Product create(@RequestBody @Valid ProductRequest productRequest);

    @GetMapping("/{productId}")
    Product findById(@PathVariable UUID productId);

    @PostMapping("/add-stock")
    public Product addStock(@RequestBody @Valid AddStockRequest addStockRequest);

}
