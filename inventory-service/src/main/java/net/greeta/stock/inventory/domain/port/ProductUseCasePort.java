package net.greeta.stock.inventory.domain.port;

import net.greeta.stock.inventory.domain.PlacedOrderEvent;
import net.greeta.stock.inventory.domain.ProductRequest;
import net.greeta.stock.inventory.domain.entity.Product;

import java.util.UUID;

public interface ProductUseCasePort {

  Product findById(UUID productId);

  Product create(ProductRequest productRequest);

  boolean reserveProduct(PlacedOrderEvent orderEvent);
}
