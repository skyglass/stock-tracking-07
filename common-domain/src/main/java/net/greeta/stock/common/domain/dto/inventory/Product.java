package net.greeta.stock.common.domain.dto.inventory;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  private UUID id;

  private int version;

  private String name;

  private int stocks;
}
