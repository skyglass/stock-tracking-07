package net.greeta.stock.inventory.domain.port;

import net.greeta.stock.common.domain.dto.inventory.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {

  Optional<Product> findProductById(UUID productId);

  Product saveProduct(Product product);
}
