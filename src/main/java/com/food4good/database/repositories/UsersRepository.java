package com.food4good.database.repositories;


import com.food4good.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByName(String  name);
    Optional<User> findByToken(String  token);
}