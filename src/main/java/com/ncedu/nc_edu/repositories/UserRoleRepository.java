package com.ncedu.nc_edu.repositories;

import com.ncedu.nc_edu.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findByRole(String role);

    @Query(value = "select r.id, r.role from users_roles ur inner join roles r on ur.role_id = r.id where ur.user_id = ?1", nativeQuery = true)
    Set<UserRole> findByUserId(UUID id);
}
