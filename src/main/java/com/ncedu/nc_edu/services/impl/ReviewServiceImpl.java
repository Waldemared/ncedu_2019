package com.ncedu.nc_edu.services.impl;

import com.ncedu.nc_edu.dto.resources.ReviewResource;
import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.Review;
import com.ncedu.nc_edu.repositories.RecipeRepository;
import com.ncedu.nc_edu.repositories.ReviewRepository;
import com.ncedu.nc_edu.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    public ReviewServiceImpl(@Autowired ReviewRepository reviewRepository, @Autowired RecipeRepository recipeRepository) {
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Review> findReviewsByRecipeId(UUID recipeId) {
        List<Review> reviews = reviewRepository.findAllByRecipe_Id(recipeId);
        return reviews;
    }

    @Override
    public List<Review> findReviewsByUserId(UUID userId) {
        List<Review> reviews = reviewRepository.findAllByUser_Id(userId);
        return reviews;
    }

    @Override
    public Review findReviewById(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityDoesNotExistsException("review"));
        return review;
    }

    @Override
    public Review updateReview(UUID reviewId, ReviewResource reviewResource) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityDoesNotExistsException("review"));
        Recipe recipe = review.getRecipe();
        recipe.setRating((recipe.getRating() * recipe.getReviewsNumber() - review.getRating() + reviewResource.getRating()) / recipe.getReviewsNumber());
        review.setRating(reviewResource.getRating());
        review.setReviewText(reviewResource.getReviewText());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityDoesNotExistsException("review"));
        Recipe recipe = recipeRepository.findById(review.getRecipe().getId()).get();
        recipe.setRating((recipe.getRating() * recipe.getReviewsNumber() - review.getRating()) / (recipe.getReviewsNumber() - 1));
        recipe.setReviewsNumber(recipe.getReviewsNumber() - 1);
        recipeRepository.save(recipe);
        reviewRepository.delete(review);
    }
}
