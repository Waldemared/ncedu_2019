package com.ncedu.nc_edu.services;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.Ingredient;

import java.util.List;
import java.util.UUID;

public interface IngredientService {
    Ingredient findById(UUID id) throws EntityDoesNotExistsException;
    List<Ingredient> findByName(String pattern);
    boolean existsById(UUID id);
    Ingredient update(Ingredient ingredient) throws EntityDoesNotExistsException;
    Ingredient add(String name);
    List<Ingredient> findAll();
}