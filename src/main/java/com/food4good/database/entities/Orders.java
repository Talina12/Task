
package com.food4good.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "food4good", name = "orders")
@Getter
@Setter
public class Orders extends AbstractEntity {


    @Column(name = "total_price")
    private String totalPrice;

    @Column
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProducts> products = new HashSet<>();
    
    @Column
    private String comments;

    public Orders() {

    }
    
    public void setProducts(Set<OrderProducts> products) {
    	this.products.clear();
    	this.products.addAll(products);
    }
}

