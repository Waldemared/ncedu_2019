package com.ncedu.nc_edu.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ingredients_recipes", schema = "public")
@Data
@NoArgsConstructor
public class IngredientsRecipes {
    @EmbeddedId
    private IngredientsRecipesId id = new IngredientsRecipesId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    @Column(name = "value_type")
    private String valueType;

    private Float value;

    public IngredientsRecipes(Recipe recipe, Ingredient ingredient) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.id = new IngredientsRecipesId(recipe.getId(), ingredient.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientsRecipes)) return false;
        IngredientsRecipes that = (IngredientsRecipes) o;
        return ingredient.equals(that.ingredient) &&
                recipe.equals(that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, recipe);
    }
}