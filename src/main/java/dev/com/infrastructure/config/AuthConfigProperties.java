package dev.com.infrastructure.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "auth")
public interface AuthConfigProperties {
    public String userTableName();
}
