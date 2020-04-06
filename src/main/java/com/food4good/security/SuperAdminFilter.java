package com.food4good.security;

import com.food4good.database.repositories.UsersRepository;

import java.util.Arrays;
import java.util.List;

public class SuperAdminFilter extends TokenCheckFilter{
UsersRepository usersRepository;

    public SuperAdminFilter(UsersRepository usersRepository) {
        super(usersRepository);
    }

    @Override
    public String getRole() {
        return "SUPER_ADMIN";
    }

    @Override
    public List<String> getSkipMethodList() {
        return Arrays.asList("swagger", "login", "api-docs","error", "admin");
    }
}


