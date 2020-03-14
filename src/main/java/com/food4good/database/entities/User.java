package com.food4good.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "food4good", name = "user")
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsersPreference> preferences = new HashSet<>();
    
    @OneToOne (fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "supplier_id")
    @JsonIgnore
    private Supplier supplier;

    public User() {
    }
}