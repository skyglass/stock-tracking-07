package net.greeta.stock.auth;

import net.greeta.stock.config.MockHelper;
import net.greeta.stock.config.auth.UserCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserCredentialsProviderTest {

    @Autowired
    private UserCredentialsProvider userCredentialsApi;

    @Autowired
    private MockHelper mockHelper;

    @Test
    public void testAdmin() {
        mockHelper.mockCredentials("admin", "admin");
        String username = userCredentialsApi.getUsername();
        String password = userCredentialsApi.getPassword();
        assertEquals("admin", username);
        assertEquals("admin", password);
    }

    @Test
    public void testUser() {
        mockHelper.mockCredentials("user", "user");
        String username = userCredentialsApi.getUsername();
        String password = userCredentialsApi.getPassword();
        assertEquals("user", username);
        assertEquals("user", password);
    }
}
