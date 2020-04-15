package com.food4good.dto.notifications;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeResultsDTO {

	private List<Error> results;
}
