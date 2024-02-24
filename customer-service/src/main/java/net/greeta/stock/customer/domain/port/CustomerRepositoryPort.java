package net.greeta.stock.customer.domain.port;

import net.greeta.stock.customer.domain.entity.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryPort {

  Optional<Customer> findCustomerById(UUID customerId);

  Customer saveCustomer(Customer customer);
}
