package com.ncedu.nc_edu.services;

import com.ncedu.nc_edu.dto.resources.RecipeSearchCriteria;
import com.ncedu.nc_edu.dto.resources.RecipeWithStepsResource;
import com.ncedu.nc_edu.dto.resources.ReviewResource;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RecipeService {
    List<Recipe> findByName(String name);

    Recipe findById(UUID id);

    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findAllOwn(User user);

    Recipe update(RecipeWithStepsResource dto);

    Recipe create(RecipeWithStepsResource dto, User owner);

    Review addReview(UUID recipeId, ReviewResource reviewResource);

    List<Review> findReviewsByRecipeId(UUID recipeId);

    Recipe cloneRecipe(UUID id, User user);

    Page<Recipe> search(RecipeSearchCriteria recipeSearchCriteria, Pageable pageable);

    boolean requestForApproval(UUID id);

    boolean removeById(UUID id);

    boolean moderatorApprove(UUID id);

    boolean moderatorDecline(UUID id);

    boolean moderatorRequestForChanges(UUID id);

    boolean moderatorCloneChanges(UUID id);

    boolean moderatorComment(UUID id, String message);

    List<Recipe> moderatorFindAll();

    Recipe moderatorFindOriginalById(UUID id);
}
