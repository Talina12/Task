package com.food4good.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Component
@ConfigurationProperties("custom-properties.notifications")
@Getter
@Setter
public class NotificationsConfig {
	private String key;
	private String url;
	
	@JsonProperty ("subscribe_url")
	private String subscribeUrl;
	
	@JsonProperty("unsubscribe_url")
	private String unsubscribeUrl;
	
	@JsonProperty("instance_info")
	private String instanceInfo;
}