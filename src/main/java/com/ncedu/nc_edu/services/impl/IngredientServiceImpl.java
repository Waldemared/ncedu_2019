package com.ncedu.nc_edu.services.impl;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.Ingredient;
import com.ncedu.nc_edu.repositories.IngredientRepository;
import com.ncedu.nc_edu.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(@Autowired IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient findById(UUID id) throws EntityDoesNotExistsException {
        return ingredientRepository.findById(id).orElseThrow(() -> new EntityDoesNotExistsException("Ingredient"));
    }

    public Ingredient update(Ingredient ingredient) throws EntityDoesNotExistsException {
        Ingredient oldIngredient = ingredientRepository.findById(ingredient.getId())
                        .orElseThrow(() -> new EntityDoesNotExistsException("Ingredient"));

        if (ingredient.getName() != null) {
            oldIngredient.setName(ingredient.getName());
        }

        return ingredientRepository.save(oldIngredient);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.ingredientRepository.existsById(id);
    }

    public List<Ingredient> findByName(String pattern) {
        return ingredientRepository.findByNameContainsIgnoreCase(pattern);
    }

    public Ingredient add(String name) {
        //unique?
        //if (ingredientRepository.findByName(name) != null) throw new IngredientAlreadyExist();
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setId(UUID.randomUUID());
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> findAll() {
        return new ArrayList<>(ingredientRepository.findAll());
    }
}
