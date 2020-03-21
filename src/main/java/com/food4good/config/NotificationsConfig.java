package com.food4good.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
@Component
@ConfigurationProperties("custom-properties.notifications")
@Getter
@Setter
public class NotificationsConfig {
	private String key;
	private String url;
}