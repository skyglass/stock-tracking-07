package net.greeta.stock.customer.domain.port;

import net.greeta.stock.common.domain.dto.customer.CustomerRequest;
import net.greeta.stock.customer.domain.PlacedOrderEvent;
import net.greeta.stock.common.domain.dto.customer.Customer;
import java.util.UUID;

public interface CustomerUseCasePort {

  Customer findById(UUID customerId);

  Customer create(CustomerRequest customerRequest);

  boolean reserveBalance(PlacedOrderEvent orderEvent);

  void compensateBalance(PlacedOrderEvent orderEvent);
}
