package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "recipes", schema = "public")
@Data
public class Recipe {
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private State state;

    private String name;
    private Integer calories;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private Float rating;
    private Integer price;

    @Column(name = "reviews_number")
    private Integer reviewsNumber;

    @Column(name = "cooking_time")
    private Integer cookingTime;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CookingMethod.class)
    @CollectionTable(name = "recipe_cooking_methods", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "method")
    private Set<CookingMethod> cookingMethods;

    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "index")
    private List<RecipeStep> steps;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IngredientsRecipes> ingredientsRecipes;

    @ManyToMany
    @JoinTable(
            name = "tags_recipes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    private Set<Tag> tags;

    @ManyToOne
    private User owner;

    public enum CookingMethod {
        OVEN,
        BLENDER,
        GRILL,
        WOK,
        MICROWAVE,
        FREEZER,
        STEAMER,
        STOVE
    }

    public enum Cuisine {
        RUSSIAN,
        ITALIAN,
        JAPANESE
    }

    public enum State {
        // UNLISTED:
        WAITING_FOR_APPROVAL,
        DRAFT,
        // PUBLIC:
        EDITED,
        PUBLISHED,
        // ACHIEVED:
        DELETED
    }

    @Column(name = "is_public")
    private boolean visible;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_ref", referencedColumnName = "id")
    private Recipe originalRef;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "originalRef")
    private Recipe clonedRef;

    private UUID pictureId;

    @Column(name = "moderator_comment")
    private String moderatorComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return this.getId().equals(recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                ", rating=" + rating +
                '}';
    }

    public Recipe() {
    }

    public Recipe(Recipe recipe) {
        this.calories = recipe.calories;
        this.carbohydrates = recipe.carbohydrates;
        this.cookingMethods = new HashSet<>(recipe.cookingMethods);
        this.cuisine = recipe.cuisine;
        this.fats = recipe.fats;
        this.cookingTime = recipe.cookingTime;
        this.id = UUID.randomUUID();
        this.ingredientsRecipes = new HashSet<>();
        recipe.ingredientsRecipes.forEach(ingredientsRecipes1 -> {
            var tempRecipe = new IngredientsRecipes();
            tempRecipe.setRecipe(this);
            tempRecipe.setIngredient(ingredientsRecipes1.getIngredient());
            tempRecipe.setValue(ingredientsRecipes1.getValue());
            tempRecipe.setValueType(ingredientsRecipes1.getValueType());
            this.ingredientsRecipes.add(tempRecipe);
        });
        this.name = recipe.name;
        this.owner = null;
        this.price = recipe.price;
        this.proteins = recipe.proteins;
        this.rating = 0f;
        this.reviewsNumber = 0;
        this.pictureId = recipe.pictureId;
        this.steps = new ArrayList<>();
        recipe.steps.forEach(recipeStep -> {
            var tempStep = new RecipeStep();
            tempStep.setId(UUID.randomUUID());
            tempStep.setRecipe(this);
            tempStep.setDescription(recipeStep.getDescription());
            tempStep.setPicture(recipeStep.getPicture());
            this.steps.add(tempStep);
        });
        this.tags = new HashSet<>(recipe.tags);
    }

    public void setSteps(List<RecipeStep> steps) {
        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }

        this.removeSteps();

        this.steps.addAll(steps);
    }

    public void removeSteps() {
        for (Iterator<RecipeStep> iterator = steps.iterator(); iterator.hasNext();) {
            RecipeStep cur = iterator.next();
            cur.setRecipe(null);
            iterator.remove();
        }
    }

    public void removeIngredientsRecipes() {
        for (Iterator<IngredientsRecipes> iterator = ingredientsRecipes.iterator(); iterator.hasNext();) {
            IngredientsRecipes cur = iterator.next();
            cur.setIngredient(null);
            cur.setRecipe(null);
            cur.setId(null);
            iterator.remove();
        }
    }
}