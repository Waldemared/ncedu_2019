package com.ncedu.nc_edu.models;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "public")
@Data
public class UserRole {
    public enum UserRoles {
        USER("ROLE_USER"),
        ADMIN("ROLE_ADMIN"),
        MODERATOR("ROLE_MODERATOR"),
        ANONYMOUS("ROLE_ANONYMOUS");

        private String value;

        UserRoles(String value) {
            this.value = value;
        }

        public String getString() {
            return this.value;
        }

        public GrantedAuthority getAuthority() {
            return new SimpleGrantedAuthority(value);
        }
    }

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "role")
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id.equals(userRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
