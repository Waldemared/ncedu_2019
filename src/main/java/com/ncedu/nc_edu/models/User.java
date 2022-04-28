package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users", schema = "public")
@Data
public class User {
    public enum Gender {
        MALE, FEMALE, UNKNOWN;
        Gender() {
        }
    }

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday")
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @OrderColumn(name = "index")
    private List<ItemCategory> categories;

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                ", enabled=" + enabled +
                '}';
    }
}
