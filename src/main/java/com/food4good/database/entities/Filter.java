package com.food4good.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


    @Entity
    @Table(schema = "food4good", name = "filter")
    @Getter
    @Setter
    public class Filter extends AbstractEntity {
        @Column
        private String name;
        @Column
        private  String type;

        public Filter() {
        }
    }
