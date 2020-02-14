package com.food4good.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("custom-properties")
public class GlobalProperties {
    private int hoursBeforeClose;

    public void setHoursBeforeClose(int hoursBeforeClose) {
        this.hoursBeforeClose = hoursBeforeClose;
    }

    public int getHoursBeforeClose() {
        return hoursBeforeClose;
    }
}