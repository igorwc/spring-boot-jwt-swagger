package com.cavalcanti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cavalcanti.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}