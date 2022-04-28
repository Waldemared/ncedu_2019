package com.ncedu.nc_edu.dto.resources;

import com.ncedu.nc_edu.dto.validators.ValueOfEnum;
import com.ncedu.nc_edu.models.Recipe;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
public class RecipeSearchCriteria {
    @PositiveOrZero(message = "Calories min must be greater than or equal to zero")
    private Integer caloriesMin;
    @PositiveOrZero(message = "Calories max must be greater than or equal to zero")
    private Integer caloriesMax;

    @PositiveOrZero(message = "Fats min must be greater than or equal to zero")
    private Float fatsMin;
    @PositiveOrZero(message = "Fats max must be greater than or equal to zero")
    private Float fatsMax;

    @PositiveOrZero(message = "Carbohydrates min must be greater than or equal to zero")
    private Float carbohydratesMin;
    @PositiveOrZero(message = "Carbohydrates max must be greater than or equal to zero")
    private Float carbohydratesMax;

    @PositiveOrZero(message = "Proteins min must be greater than or equal to zero")
    private Float proteinsMin;
    @PositiveOrZero(message = "Proteins max must be greater than or equal to zero")
    private Float proteinsMax;

    @PositiveOrZero(message = "Rating min must be greater than or equal to zero")
    private Float ratingMin;

    @PositiveOrZero(message = "Rating max must be greater than or equal to zero")
    private Float ratingMax;

    @Size(min = 1, max = 128, message = "Name size must be 1..128")
    private String name;

    private Set<
            @ValueOfEnum(value = Recipe.CookingMethod.class, message = "Cooking method must be any of " +
            "OVEN|BLENDER|GRILL|WOK|MICROWAVE|FREEZER|STEAMER|STOVE") String
            > cookingMethods;

    @PositiveOrZero(message = "Cooking time min must be greater than or equal to zero")
    private Integer cookingTimeMin;
    @PositiveOrZero(message = "Cooking time max must be greater than or equal to zero")
    private Integer cookingTimeMax;

    @PositiveOrZero(message = "Price min must be greater than or equal to zero")
    private Integer priceMin;
    @PositiveOrZero(message = "Price max must be greater than or equal to zero")
    private Integer priceMax;

    private Set<String> includeTags;
    private Set<String> excludeTags;

    private Set<UUID> includeIngredients;
    private Set<UUID> excludeIngredients;

    private Set<
            @ValueOfEnum(value = Recipe.Cuisine.class, message = "Cuisine must be any of " +
                    "RUSSIAN|ITALIAN|JAPANESE") String
            > cuisines;

    public boolean hasAnyCriteria() {
        return caloriesMax != null ||
                caloriesMin != null ||
                fatsMin != null ||
                fatsMax != null ||
                carbohydratesMin != null ||
                carbohydratesMax != null ||
                proteinsMin != null ||
                proteinsMax != null ||
                ratingMin != null ||
                ratingMax != null ||
                name != null ||
                cookingMethods != null ||
                cookingTimeMin != null ||
                cookingTimeMax != null ||
                priceMin != null ||
                priceMax != null ||
                includeTags != null ||
                excludeTags != null ||
                includeIngredients != null ||
                excludeIngredients != null ||
                cuisines != null;
    }
}
