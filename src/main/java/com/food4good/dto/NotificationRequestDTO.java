package com.food4good.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationRequestDTO {
	private String priority;
	private String to;
	private AppNotification notification;
	
	@Getter
	@Setter
	@NoArgsConstructor
	public class AppNotification{
		private String body;
		private String title;
		private String icon;
	}
	
	public NotificationRequestDTO(NotificationDTO notificationDTO) {
		this.notification = new AppNotification();
		this.to = notificationDTO.getToken();
		this.notification.body = notificationDTO.getText();
		this.notification.title = notificationDTO.getTitle();
		}

}
