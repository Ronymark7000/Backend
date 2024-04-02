package com.project.JewelHub.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByContact(long contact);
    @Query(value = "SELECT * FROM jeweluser WHERE email = :email AND password = :password LIMIT 1", nativeQuery = true)
    Optional<User> findUserByEmailAndPassword(String email, String password);
}

