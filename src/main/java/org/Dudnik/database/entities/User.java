package org.Dudnik.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.Dudnik.database.converters.PasswordConverter;
import org.Dudnik.enums.Roles;


import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "users")
public class User extends AbstractEntity{

    private String name;

    private String email;

    @Convert(converter = PasswordConverter.class)
    private String password;

    //private Roles role;
}
