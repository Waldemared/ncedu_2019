package com.ncedu.nc_edu.repositories;

import com.ncedu.nc_edu.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Ingredient findByName(String name);
    Ingredient findByNameIgnoreCase(String name);
    List<Ingredient> findByNameContainsIgnoreCase(String pattern);
}
