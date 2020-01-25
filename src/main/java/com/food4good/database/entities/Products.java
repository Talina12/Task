package com.food4good.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "food4good", name = "products")
@Getter
@Setter
public class Products extends  AbstractEntity{

    @Column
    private String name;

    @Column
    private String description;

    @Column(name="fix_price")
    private String fixPrice;

    @Column(name="min_price")
    private String minPrice;

    @Column(name="max_price")
    private String maxPrice;

    @Column
    private Integer amount;

    @Column (name="original_price")
    private String origPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    public Products() {
    }
}