package com.ncedu.nc_edu.repositories;

import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
    List<Recipe> findByNameContaining(String name);

    List<Recipe> findByOwner(User user);

    Page<Recipe> findAll(Pageable pageable);

    Page<Recipe> findAllByVisibleIsTrue(Pageable pageable);

    List<Recipe> findAllByStateInAndOriginalRefIsNull(Set<Recipe.State> states);
}
