package net.greeta.stock.config;

import net.greeta.stock.config.auth.UserCredentialsProvider;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockHelper {

    @Autowired
    private UserCredentialsProvider userCredentialsProvider;

    public void mockCredentials(String username, String password) {
        Mockito.doReturn(username).when(userCredentialsProvider).getUsername();
        Mockito.doReturn(password).when(userCredentialsProvider).getPassword();
    }

}
