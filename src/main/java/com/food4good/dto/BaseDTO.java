package com.food4good.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDTO {
    private String createdAt;
    private String updatedAt;
    private long id;
}
