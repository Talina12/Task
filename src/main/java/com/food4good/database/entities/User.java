package com.food4good.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "food4good", name = "users")
@Getter
@Setter
public class User extends  AbstractEntity{
    @Column

    private String token;

    @Column
    private String name;

    @Column
    private String phone_number;

    @Column
    private String udid;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String roles;

    public User() {
    }
}