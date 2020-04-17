package com.food4good.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.Preference;
import com.food4good.database.entities.User;
import com.food4good.database.entities.UsersPreference;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UsersPreference, Long>{
	public Optional<UsersPreference> findByUser(User user);
}
