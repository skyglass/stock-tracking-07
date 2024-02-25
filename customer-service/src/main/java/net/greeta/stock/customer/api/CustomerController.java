package net.greeta.stock.customer.api;

import net.greeta.stock.common.domain.dto.customer.CustomerRequest;
import net.greeta.stock.common.domain.dto.customer.Customer;
import net.greeta.stock.customer.domain.port.CustomerUseCasePort;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerUseCasePort customerUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer create(@RequestBody @Valid CustomerRequest customerRequest) {
    return customerUseCase.create(customerRequest);
  }

  @GetMapping("/{customerId}")
  public Customer findById(@PathVariable UUID customerId) {
    return customerUseCase.findById(customerId);
  }
}
