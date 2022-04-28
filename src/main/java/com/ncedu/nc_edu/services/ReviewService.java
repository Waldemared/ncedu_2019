package com.ncedu.nc_edu.services;

import com.ncedu.nc_edu.dto.resources.ReviewResource;
import com.ncedu.nc_edu.models.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    List<Review> findReviewsByRecipeId(UUID recipeId);
    List<Review> findReviewsByUserId(UUID userId);
    Review findReviewById(UUID reviewId);
    Review updateReview(UUID reviewId, ReviewResource reviewResource);
    void deleteReview(UUID reviewId);
}
