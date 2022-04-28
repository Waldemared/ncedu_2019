package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "ingredients", schema = "public")
public class Ingredient extends RepresentationModel<Ingredient> {
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}