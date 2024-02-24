package net.greeta.stock.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CustomerJdbcConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.customer-payment")
    public DataSourceProperties customerPaymentDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.customer-payment.hikari")
    public DataSource customerPaymentDataSource() {
        return customerPaymentDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate customerPaymentJdbcTemplate(@Qualifier("customerPaymentDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
