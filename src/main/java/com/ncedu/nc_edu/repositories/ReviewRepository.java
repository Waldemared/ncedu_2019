package com.ncedu.nc_edu.repositories;

import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUser_Id(UUID userId);
    List<Review> findAllByRecipe_Id(UUID recipeId);
    Optional<Review> findByUserAndRecipe(User user, Recipe recipe);
}
