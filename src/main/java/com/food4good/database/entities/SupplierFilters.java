package com.food4good.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


    @Entity
    @Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"filter_id", "supplier_id"})},
            schema = "food4good", name = "supplierFilters")
    @Getter
    @Setter
    public class SupplierFilters extends AbstractEntity {

        @ManyToOne
        @JoinColumn(name = "filter_id", nullable = false)
        @JsonIgnore
        private Filter filter;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "supplier_id", nullable = false)
        @JsonIgnore
        private Supplier supplier;


        public SupplierFilters() {
        }
}
