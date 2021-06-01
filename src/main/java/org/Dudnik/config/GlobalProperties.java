package org.Dudnik.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "GlobalProperties")
public class GlobalProperties {
    private String encriptionAlgorithm;
    private String key;
}

