package com.food4good.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.Formula;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "food4good", name = "supplier")
@Getter
@Setter
public class Supplier extends  AbstractEntity{
    @Column
    private String name;

    @Column
    private String address;

    @Column(name = "open_hours")
    private String openHours;

    @Column (name = "back_ground_image")
    private String backGroundImage;

    @Column (name = "logo_image")
    private String logoImage;

    @Column
    private String longtitude;

    @Column
    private String latetude;

    @Column
    private String rates;

    @Column(name="display_order")
    private String displayOrder;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Products> products = new HashSet<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupplierFilters> filters = new HashSet<>();
    
    @Column (name="is_Active")
    private boolean isActive;
    
    public Supplier() {
    }
}