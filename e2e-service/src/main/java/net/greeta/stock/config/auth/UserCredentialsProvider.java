package net.greeta.stock.config.auth;

public interface UserCredentialsProvider {

    public String getUsername();

    public String getPassword();
}
