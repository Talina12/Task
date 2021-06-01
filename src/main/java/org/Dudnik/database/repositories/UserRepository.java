package org.Dudnik.database.repositories;

import org.Dudnik.database.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//I know to take action to prevent SQL injection, but I have not found how to do this when dealing with hibernate repositories
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
