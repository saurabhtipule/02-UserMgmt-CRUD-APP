package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.UserAccountEntity;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Integer> {

	public UserAccountEntity findByEmailAndPassword(String email, String password);

	public UserAccountEntity findByEmail(String email);

}
