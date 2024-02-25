package net.greeta.stock.inventory.domain.port;

import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.inventory.domain.PlacedOrderEvent;
import net.greeta.stock.common.domain.dto.inventory.ProductRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;

import java.util.UUID;

public interface ProductUseCasePort {

  Product findById(UUID productId);

  Product create(ProductRequest productRequest);

  Product addStock(AddStockRequest addStockRequest);

  boolean reserveProduct(PlacedOrderEvent orderEvent);
}
