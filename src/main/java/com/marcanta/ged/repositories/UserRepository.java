package com.marcanta.ged.repositories;

import com.marcanta.ged.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
