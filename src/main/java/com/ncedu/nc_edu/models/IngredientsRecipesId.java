package com.ncedu.nc_edu.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsRecipesId implements Serializable {
    @Column(name = "recipe_id")
    @Type(type = "uuid-char")
    private UUID recipeId;

    @Column(name = "ingredient_id")
    @Type(type = "uuid-char")
    private UUID ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientsRecipesId that = (IngredientsRecipesId) o;
        return recipeId.equals(that.recipeId) &&
                ingredientId.equals(that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }
}