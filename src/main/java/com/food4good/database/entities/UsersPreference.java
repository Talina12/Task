package com.food4good.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
    @Entity
    @Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "preference_id"})},
            schema = "food4good", name = "usersPreference")
    @Getter
    @Setter
    public class UsersPreference extends AbstractEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        @JsonIgnore
        private User user;

        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "preference_id", nullable = false)
        @JsonIgnore
        private Preference preference;*/

    @Entity
    @Table(schema = "food4good", name = "usersPreference")
    @Getter
    @Setter
    public class UsersPreference extends AbstractEntity {
    	
    	@OneToOne (fetch = FetchType.LAZY, optional = true)
        @JoinColumn(name = "user_id", nullable = false)
        @JsonIgnore
        private User user;
    	
    	
        @Column
        private boolean sendPush;


        public UsersPreference() {
        	//Don't forget to set fields with default values
        }
}
