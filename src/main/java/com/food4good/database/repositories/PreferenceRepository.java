package com.food4good.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.Preference;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long>{
	public Optional<Preference> findById(Long id); 
}
