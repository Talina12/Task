package com.food4good.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "food4good", name = "dishes")
@Getter
@Setter
public class Dish extends  AbstractEntity{

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String price;

    @Column
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    public Dish() {
    }
}