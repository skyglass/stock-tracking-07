package net.greeta.stock.inventory.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.greeta.stock.common.domain.dto.inventory.AddStockRequest;
import net.greeta.stock.common.domain.dto.inventory.ProductRequest;
import net.greeta.stock.common.domain.dto.inventory.Product;
import net.greeta.stock.inventory.domain.exception.NotEnoughStockException;
import net.greeta.stock.inventory.domain.exception.NotFoundException;
import net.greeta.stock.inventory.domain.port.ProductRepositoryPort;
import net.greeta.stock.inventory.domain.port.ProductUseCasePort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductUseCase implements ProductUseCasePort {

  private final ObjectMapper mapper;

  private final ProductRepositoryPort productRepository;

  @Override
  public Product findById(UUID productId) {
    return productRepository.findProductById(productId).orElseThrow(NotFoundException::new);
  }

  @Override
  public Product create(ProductRequest productRequest) {
    var product = mapper.convertValue(productRequest, Product.class);
    product.setId(UUID.randomUUID());
    return productRepository.saveProduct(product);
  }

  @Override
  public Product addStock(AddStockRequest addStockRequest) {
    var product = findById(addStockRequest.productId());
    if (addStockRequest.quantity() <= 0) {
      throw new RuntimeException("AddStockRequest error (productId = %s, quantity = %s): " +
              "quantity should be positive".formatted(addStockRequest.productId(), addStockRequest.quantity()));
    }
    product.setStocks(product.getStocks() + addStockRequest.quantity());
    return productRepository.saveProduct(product);
  }

  @Override
  public boolean reserveProduct(PlacedOrderEvent orderEvent) {
    var product = findById(orderEvent.productId());
    if (product.getStocks() - orderEvent.quantity() < 0) {
      throw new NotEnoughStockException(product.getId(), product.getStocks(), orderEvent.quantity());
    }
    product.setStocks(product.getStocks() - orderEvent.quantity());
    productRepository.saveProduct(product);
    return true;
  }
}
