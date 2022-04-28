package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ration_categories", schema = "public")
@Data
public class ItemCategory {
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCategory that = (ItemCategory) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
