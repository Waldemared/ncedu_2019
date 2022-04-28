package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Table(name = "ration_items", schema = "public")
public class RationItem {
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    private Date date;

    @ManyToOne
    private ItemCategory category;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Recipe recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RationItem that = (RationItem) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RationItem{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
