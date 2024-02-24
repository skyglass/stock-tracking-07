package net.greeta.stock.inventory;

import net.greeta.stock.common.domain.dto.inventory.InventoryAddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.ProductDetails;
import net.greeta.stock.common.domain.dto.inventory.ProductInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory2")
public interface InventoryClient2 {

    @PostMapping("/add-stock")
    public ProductInventoryDto addStock(@RequestBody InventoryAddStockRequest request);

    @GetMapping("{productId}")
    public ProductDetails getProductDetails(@PathVariable Integer productId);
}
