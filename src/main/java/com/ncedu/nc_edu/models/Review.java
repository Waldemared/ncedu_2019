package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Table(name = "recipe_reviews", schema = "public")
public class Review {
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    private Date created_on;

    private Float rating;

    @Column(name = "review")
    private String reviewText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review that = (Review) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "id=" + id +
                ", created_on=" + created_on +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
