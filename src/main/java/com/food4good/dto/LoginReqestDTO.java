package com.food4good.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqestDTO {
    String udid;
    String email;
    String password;
}
