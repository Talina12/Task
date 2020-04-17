package com.food4good.dto.notifications;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppInstanceInfoDTO {

	private String applicationVersion;
	private String application;
	private String scope;
	private String authorizedEntity;
	private String appSigner;
	private String platform;
	private Rel rel;
}