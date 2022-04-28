package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.dto.assemblers.RecipeAssembler;
import com.ncedu.nc_edu.dto.assemblers.RecipeStepAssembler;
import com.ncedu.nc_edu.dto.resources.RecipeResource;
import com.ncedu.nc_edu.dto.resources.RecipeStepResource;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {
    private final RecipeService recipeService;
    private final RecipeAssembler recipeAssembler;
    private final RecipeStepAssembler recipeStepAssembler;

    @Autowired
    public ModeratorController(RecipeService recipeService, RecipeAssembler recipeAssembler, RecipeStepAssembler recipeStepAssembler) {
        this.recipeService = recipeService;
        this.recipeAssembler = recipeAssembler;
        this.recipeStepAssembler = recipeStepAssembler;
    }

    public ResponseEntity<?> moderatorRoot() {
        return ResponseEntity.ok("md rt");
    }

    @PostMapping("/recipes/{id}/approve")
    public ResponseEntity<Void> approveRecipeOrChanges(@PathVariable UUID id) {
        boolean status = this.recipeService.moderatorApprove(id);

        return this.getResponseEntityByStatus(status);
    }

    @PostMapping("/recipes/{id}/changesNeeded")
    public ResponseEntity<Void> requestForRecipeChanges(@PathVariable UUID id) {
        boolean status = this.recipeService.moderatorRequestForChanges(id);

        return this.getResponseEntityByStatus(status);
    }

    @PutMapping("/recipes/{id}/comment")
    public ResponseEntity<Void> recipeComment(@PathVariable UUID id, @RequestParam @Size(max = 512) String message) {
        boolean status = this.recipeService.moderatorComment(id, message);

        return this.getResponseEntityByStatus(status);
    }

    @PostMapping("/recipes/{id}/delete")
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        boolean status = this.recipeService.removeById(id);

        return this.getResponseEntityByStatus(status);
    }

    @PostMapping("/recipes/{id}/decline")
    public ResponseEntity<Void> declineChangesOrApproval(@PathVariable UUID id) {
        boolean status = this.recipeService.moderatorDecline(id);

        return this.getResponseEntityByStatus(status);
    }

    @PostMapping("/recipes/{id}/cloneChanges")
    public ResponseEntity<Void> cloneRecipeChanges(@PathVariable UUID id) {
        boolean status = this.recipeService.moderatorCloneChanges(id);

        return this.getResponseEntityByStatus(status);
    }

    @GetMapping("/recipes")
    public ResponseEntity<CollectionModel<RecipeResource>> getAllRecipes() {
        List<Recipe> recipes = this.recipeService.moderatorFindAll();

        return ResponseEntity.ok().body(recipeAssembler.toCollectionModel(recipes));
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeResource> getRecipe(@PathVariable UUID id) {
        Recipe recipe = this.recipeService.moderatorFindOriginalById(id);

        return ResponseEntity.ok().body(recipeAssembler.toModel(recipe));
    }

    @GetMapping("/recipes/{id}/steps")
    public CollectionModel<RecipeStepResource> getRecipeSteps(@PathVariable UUID id) {
        CollectionModel<RecipeStepResource> resource = new CollectionModel<>(
                this.recipeService.moderatorFindOriginalById(id).getSteps().stream()
                        .map(recipeStepAssembler::toModel).collect(Collectors.toList())
        );

        resource.add(linkTo(methodOn(RecipeController.class).getById(null, id)).withRel("recipe"));

        return resource;
    }

    private ResponseEntity<Void> getResponseEntityByStatus(boolean status) {
        if (status) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
