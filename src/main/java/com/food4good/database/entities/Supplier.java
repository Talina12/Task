package com.food4good.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "food4good", name = "suppliers")
@Getter
@Setter
public class Supplier extends  AbstractEntity{
    @Column
    private String name;

    @Column
    private String address;

    @Column(name = "open_hours")
    private String openHours;

    @Column
    private String image;

    @Column
    private String longtitude;

    @Column
    private String latetude;

    @Column
    private String rate;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dish> dishes = new HashSet<>();

    public Supplier() {
    }
}