package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.dto.assemblers.IngredientAssembler;
import com.ncedu.nc_edu.dto.resources.IngredientResource;
import com.ncedu.nc_edu.models.Ingredient;
import com.ncedu.nc_edu.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientAssembler ingredientAssembler;

    IngredientController(@Autowired IngredientService ingredientService,
                         @Autowired  IngredientAssembler ingredientAssembler) {
        this.ingredientService = ingredientService;
        this.ingredientAssembler = ingredientAssembler;
    }

    @GetMapping(value = "/ingredients")
    public CollectionModel<IngredientResource> getAll(@RequestParam(required = false) String name) {
        List<Ingredient> ingredientEntities;

        if (name != null) {
            ingredientEntities = ingredientService.findByName(name);
        } else {
            ingredientEntities = ingredientService.findAll();
        }

        if (ingredientEntities.size() == 0) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(IngredientResource.class);
            List<EmbeddedWrapper> list = Collections.singletonList(wrapper);
            return new CollectionModel(list);
        }

        CollectionModel<IngredientResource> resource = new CollectionModel<>(ingredientEntities.stream()
                .map(ingredientAssembler::toModel).collect(Collectors.toList()));

        return resource;
    }

    @GetMapping(value = "/ingredients/{id}")
    public Ingredient getById(@PathVariable UUID id) {
        return ingredientService.findById(id);
    }

    @PostMapping(value = "/ingredients")
    public Ingredient add(@RequestParam @NotNull String name) {
        return ingredientService.add(name);
    }

    @PutMapping(value = "/ingredients/{id}")
    public Ingredient update(@PathVariable UUID id, @RequestBody Ingredient ingredient) {
        return ingredientService.update(ingredient);
    }
}
