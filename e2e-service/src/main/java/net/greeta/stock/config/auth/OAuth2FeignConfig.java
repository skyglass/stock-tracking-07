package net.greeta.stock.config.auth;

import feign.RequestInterceptor;
import net.greeta.stock.client.OAuth2Client;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OAuth2FeignConfig {

    @Value("${spring.cloud.openfeign.client.config.oauth2.url}")
    private String oauth2ClientUrl;

    @Autowired
    private OAuth2Client oAuth2Client;

    @Autowired
    private UserCredentialsProvider userCredentialsProvider;

    @Value("${security.oauth2.client-id}")
    private String securityOauth2ClientId;

    @Value("${security.oauth2.grant-type}")
    private String securityOauth2GrantType;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (!StringUtils.startsWith(requestTemplate.url(), oauth2ClientUrl)) {
                requestTemplate.header(
                        "Authorization", "Bearer " + getAccessToken());
            }
        };
    }

    private String getAccessToken() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", userCredentialsProvider.getUsername());
        params.put("password", userCredentialsProvider.getPassword());
        params.put("grant_type", securityOauth2GrantType);
        params.put("client_id", securityOauth2ClientId);
        var tokenDto = oAuth2Client.getToken(params);
        return tokenDto.getAccessToken();
    }


}
