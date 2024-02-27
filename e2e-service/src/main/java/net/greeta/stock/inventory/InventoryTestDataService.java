package net.greeta.stock.inventory;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.testdata.JdbcTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryTestDataService extends JdbcTestDataService {

    @Autowired
    @Qualifier("inventoryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void resetDatabase() {
        executeString("DELETE FROM message_log");
        executeString("DELETE FROM out_box");
        executeString("DELETE FROM products");
    }

}
