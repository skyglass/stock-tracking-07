package net.greeta.stock.inventory.infrastructure.message.outbox;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutBoxRepository extends JpaRepository<OutBox, UUID> {

}
