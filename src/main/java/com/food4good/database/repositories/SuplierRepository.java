package com.food4good.database.repositories;


import com.food4good.database.entities.Suplier;
import com.food4good.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuplierRepository extends JpaRepository<Suplier, Long> {

    Optional<Suplier> findById(int id);
    List<Suplier> findAll();
}