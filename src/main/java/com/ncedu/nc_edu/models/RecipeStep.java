package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Table(name = "recipe_steps", schema = "public")
public class RecipeStep {
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    private String description;

    @ManyToOne
    private Recipe recipe;

    @Type(type = "uuid-char")
    private UUID picture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeStep)) return false;
        RecipeStep that = (RecipeStep) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RecipeStep{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", picture=" + picture +
                '}';
    }
}
