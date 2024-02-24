package net.greeta.stock.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsDefaultProvider implements UserCredentialsProvider {

    @Value("${security.oauth2.username}")
    private String securityOauth2Username;

    @Value("${security.oauth2.password}")
    private String securityOauth2Password;

    @Override
    public String getUsername() {
        return securityOauth2Username;
    }

    @Override
    public String getPassword() {
        return securityOauth2Password;
    }
}
