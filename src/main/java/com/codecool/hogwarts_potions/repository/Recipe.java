package com.codecool.hogwarts_potions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Recipe extends JpaRepository<Recipe, Long> {
}
