package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.dto.assemblers.RecipeAssembler;
import com.ncedu.nc_edu.dto.assemblers.RecipeStepAssembler;
import com.ncedu.nc_edu.dto.assemblers.ReviewAssembler;
import com.ncedu.nc_edu.dto.resources.*;
import com.ncedu.nc_edu.exceptions.RequestParseException;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.security.CustomUserDetails;
import com.ncedu.nc_edu.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeAssembler recipeAssembler;
    private final ReviewAssembler reviewAssembler;
    private final RecipeStepAssembler recipeStepAssembler;

    public RecipeController(
            @Autowired RecipeService recipeService,
            @Autowired RecipeAssembler recipeAssembler,
            @Autowired ReviewAssembler reviewAssembler,
            @Autowired RecipeStepAssembler recipeStepAssembler
    ) {
        this.recipeService = recipeService;
        this.recipeAssembler = recipeAssembler;
        this.reviewAssembler = reviewAssembler;
        this.recipeStepAssembler = recipeStepAssembler;
    }

    @GetMapping("/recipes")
    public PagedModel<EntityModel<RecipeResource>> getAll(
            Authentication auth,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<Recipe> page = this.recipeService.findAll(pageable);

        PagedModel<EntityModel<RecipeResource>> paged = PagedModel.wrap(page.getContent().stream().map(recipeAssembler::toModel)
                .collect(Collectors.toList()), new PagedModel.PageMetadata(
                page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages()
        ));

        paged.add(linkTo(methodOn(RecipeController.class).create(auth, null)).withRel("create"));

        return addPageLinks(request, page, paged);
    }

    @GetMapping(value = "/recipes/{id}")
    public RecipeResource getById(Authentication auth, @PathVariable UUID id) {
        RecipeResource resource = this.recipeAssembler.toModel(this.recipeService.findById(id));
        return resource;
    }

    @GetMapping(value = "/recipes/{recipeId}/reviews")
    public ResponseEntity<CollectionModel<ReviewResource>> getReviews(@PathVariable UUID recipeId) {
        CollectionModel<ReviewResource> resource = reviewAssembler.toCollectionModel(recipeService.findReviewsByRecipeId(recipeId));
        return ResponseEntity.ok(resource);
    }

    @PostMapping(value = "/recipes/{recipeId}/reviews")
    public ResponseEntity<RepresentationModel<ReviewResource>> addReview(@PathVariable UUID recipeId,
                                                                         @RequestBody @Valid ReviewResource reviewResource) {
        ReviewResource resource = reviewAssembler.toModel(recipeService.addReview(recipeId, reviewResource));
        return ResponseEntity.ok(resource);
    }

    @PutMapping(value = "/recipes/{id}/requestForApproval")
    public ResponseEntity<Void> requestForApproval(@PathVariable UUID id) {
        boolean status = this.recipeService.requestForApproval(id);

        if (status) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/recipes/{id}/steps")
    public CollectionModel<RecipeStepResource> getRecipeSteps(@PathVariable UUID id) {
        CollectionModel<RecipeStepResource> resource = new CollectionModel<>(
                this.recipeService.findById(id).getSteps().stream()
                        .map(recipeStepAssembler::toModel).collect(Collectors.toList())
        );

        if (resource.getContent().size() == 0) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(RecipeStepResource.class);
            List<EmbeddedWrapper> list = Collections.singletonList(wrapper);
            return new CollectionModel(list);
        }

        resource.add(linkTo(methodOn(RecipeController.class).getById(null, id)).withRel("recipe"));

        return resource;
    }

    @PostMapping(value = "/recipes")
    public RecipeResource create(Authentication auth, @RequestBody @Valid RecipeWithStepsResource recipe) {
        User user = ((CustomUserDetails)(auth.getPrincipal())).getUser();

        if (recipe.getSteps() == null) {
            throw new RequestParseException("Recipe must contain at least 1 step");
        }

        recipe.getSteps().forEach(step -> {
            if (step.getDescription() == null && step.getPicture() == null) {
                throw new RequestParseException("Step must contain either picture or description");
            }
        });

        if (recipe.getInfo().getCookingMethods() == null) {
            throw new RequestParseException("Recipe must contain at least 1 cooking method");
        } else {
            if (recipe.getInfo().getCookingMethods().size() == 0) {
                throw new RequestParseException("Recipe must contain at least 1 cooking method");
            }
        }

        return this.recipeAssembler.toModel(this.recipeService.create(recipe, user));
    }

    @PutMapping(value = "/recipes/{id}")
    public RecipeResource update(
            Authentication auth,
            @PathVariable UUID id,
            @RequestBody @Valid RecipeWithStepsResource recipe
    ) {
        if (recipe.getSteps() == null) {
            throw new RequestParseException("Recipe must contain at least 1 step");
        }

        recipe.getSteps().forEach(step -> {
            if (step.getDescription() == null && step.getPicture() == null) {
                throw new RequestParseException("Step must contain either picture or description");
            }
        });

        recipe.getInfo().setId(id);

        return this.recipeAssembler.toModel(this.recipeService.update(recipe));
    }

    @DeleteMapping(value = "/recipes/{id}")
    public ResponseEntity<Void> remove(Authentication auth, @PathVariable UUID id) {
        this.recipeService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/recipes/search")
    public PagedModel<EntityModel<RecipeResource>> search(
            Authentication auth,
            @Valid RecipeSearchCriteria recipeSearchCriteria,
            Pageable pageable,
            HttpServletRequest request
    ) {
        if (recipeSearchCriteria == null) {
            return this.getAll(auth, pageable, request);
        }

        if (!recipeSearchCriteria.hasAnyCriteria()) {
            return this.getAll(auth, pageable, request);
        }

        Page<Recipe> page = recipeService.search(recipeSearchCriteria, pageable);

        PagedModel<EntityModel<RecipeResource>> paged = PagedModel.wrap(page.getContent().stream().map(recipeAssembler::toModel)
                        .collect(Collectors.toList()), new PagedModel.PageMetadata(
                                page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages())
        );

        return addPageLinks(request, page, paged);
    }

    private PagedModel<EntityModel<RecipeResource>> addPageLinks(HttpServletRequest request, Page<Recipe> page,
                                                                 PagedModel<EntityModel<RecipeResource>> paged
    ) {
        if (page.getTotalPages() == 0) {
            return paged;
        }

        String queryString = request.getQueryString();
        queryString = "?" + queryString;

        if (queryString.length() != 1) {
            queryString += "&";
        }

        if (page.hasNext()) {
            String next = "page=" + (page.getNumber() + 1) + "&size=" + page.getSize();
            paged.add(linkTo(methodOn(RecipeController.class).search(null, null,
                    null, null))
                    .slash(queryString + next)
                    .withRel("next"));
        }

        if (page.hasPrevious()) {
            String prev = "page=" + (page.getNumber() - 1) + "&size=" + page.getSize();
            if (!queryString.endsWith("&"))
                queryString += "&";

            paged.add(linkTo(methodOn(RecipeController.class).search(null, null,
                    null, null))
                    .slash(queryString + prev)
                    .withRel("prev"));
        }

        if (!queryString.endsWith("&"))
            queryString += "&";

        String first = "page=0&size=" + page.getSize();

        paged.add(linkTo(methodOn(RecipeController.class).search(null, null,
                null, null))
                .slash(queryString + first)
                .withRel("first"));


        if (!queryString.endsWith("&"))
            queryString += "&";

        String last = "page=" + (page.getTotalPages() - 1) + "&size=" + page.getSize();

        paged.add(linkTo(methodOn(RecipeController.class).search(null, null,
                null, null))
                .slash(queryString + last)
                .withRel("last"));

        return paged;
    }

    @PostMapping(value = "/recipes/{id}/clone")
    public RecipeResource cloneRecipe(
            Authentication auth,
            @PathVariable UUID id
    ) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        var tmp = this.recipeService.cloneRecipe(id, user);
        return this.recipeAssembler.toModel(tmp);
    }

    @GetMapping("/recipes/cookingMethods")
    public CollectionModel<String> getAvailableCookingMethods() {
        return new CollectionModel<>(
                Stream.of(Recipe.CookingMethod.values()).map(Enum::toString).collect(Collectors.toSet())
        );
    }

    @GetMapping("/recipes/cuisines")
    public CollectionModel<String> getAvailableCuisines() {
        return new CollectionModel<>(
                Stream.of(Recipe.Cuisine.values()).map(Enum::toString).collect(Collectors.toSet())
        );
    }
}
