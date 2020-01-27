package com.food4good.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(schema = "food4good", name = "orderProducts")
@Getter
@Setter
public class OrderProducts extends AbstractEntity {

        @Column
        private String price;

        @Column
        private Integer amount;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id", nullable = false)
        @JsonIgnore
        private Orders orders;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id", nullable = false)
        @JsonIgnore
        private Products products;

        public OrderProducts() {

        }
}
