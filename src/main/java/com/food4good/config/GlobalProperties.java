package com.food4good.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("custom-properties")
public class GlobalProperties {
    private int hoursBeforeClose;
    
    @JsonProperty("max_num_of_dishes")
    private int maxNumOfDishes;
    
    @JsonProperty("super_admin_token")
    private String superAdminToken;
}