package com.food4good.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


    @Entity
    @Table(schema = "food4good", name = "preference")
    @Getter
    @Setter
    public class Preference extends AbstractEntity {
        @Column
        private boolean sendPush;

        public Preference() {
        }
    }

