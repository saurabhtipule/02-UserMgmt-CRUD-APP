package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.CountryMasterEntity;

public interface CountryMasterRepository extends JpaRepository<CountryMasterEntity, Integer> {

}
