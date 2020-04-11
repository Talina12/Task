
package com.food4good.security;

import com.food4good.config.Roles;
import com.food4good.database.repositories.UsersRepository;

import java.util.Arrays;
import java.util.List;

public class AdminFilter extends TokenCheckFilter{

	UsersRepository usersRepository;


    public AdminFilter(UsersRepository usersRepository) {
        super(usersRepository);
    }

    @Override
    public String getRole() {
        return Roles.ADMIN.toString();
    }

    @Override
    public List<String> getSkipMethodList() {
        return Arrays.asList("swagger", "login", "api-docs","error", "superAdmin");
    }
}
