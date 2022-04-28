package com.ncedu.nc_edu.security;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.models.UserRole.UserRoles;
import com.ncedu.nc_edu.repositories.RecipeRepository;
import com.ncedu.nc_edu.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class SecurityAccessResolverImpl implements SecurityAccessResolver {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    public SecurityAccessResolverImpl(@Autowired ReviewRepository reviewRepository,
                                      @Autowired RecipeRepository recipeRepository) {
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getUser() {
        return getAuthorities().contains(UserRoles.ANONYMOUS.getAuthority()) ?
                null : ((CustomUserDetails) getAuthentication().getPrincipal()).getUser();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return new HashSet<>(getAuthentication().getAuthorities());
    }

    @Override
    public boolean isSelf(UUID id) {
        return getUser() != null && getUser().getId().equals(id);
    }

    @Override
    public boolean isModerator() {
        return getAuthorities().contains(UserRoles.MODERATOR.getAuthority());
    }

    @Override
    public boolean isAdmin() {
        return getAuthorities().contains(UserRoles.ADMIN.getAuthority());
    }

    @Override
    public boolean isAdminOrModerator() {
        Set<GrantedAuthority> authorities = getAuthorities();
        return authorities.contains(UserRoles.ADMIN.getAuthority())
                || authorities.contains(UserRoles.MODERATOR.getAuthority());
    }

    @Override
    public boolean isSelfOrGranted(UUID id) {
        User user = getUser();
        return isAdminOrModerator() || (user != null && user.getId().equals(id));
    }

    @Override
    public boolean hasAnyReview(UUID recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityDoesNotExistsException("recipe"));
        return reviewRepository.findByUserAndRecipe(getUser(), recipe).isEmpty();
    }

    @Override
    public boolean isReviewOwner(UUID reviewId) {
        User user = getUser();
        return user != null && reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityDoesNotExistsException("review"))
                .getUser().getId().equals(user.getId());
    }

    @Override
    public boolean isReviewOwnerOrGranted(UUID reviewId) {
        return isAdminOrModerator() || isReviewOwner(reviewId);
    }

    @Override
    public boolean isRecipeOwner(UUID recipeId) {
        User user = getUser();
        return  user != null && recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityDoesNotExistsException("recipe"))
                .getOwner().getId().equals(user.getId());
    }

    @Override
    public boolean isRecipeOwnerOrGranted(UUID recipeId) {
        return isAdminOrModerator() || isRecipeOwner(recipeId);
    }

    @Override
    public GrantedAuthority getHeadAuthority() {
        Set<GrantedAuthority> authorities = new HashSet<>(getAuthorities());
        if (authorities.contains(UserRoles.ADMIN.getAuthority()))
            return UserRoles.ADMIN.getAuthority();
        if (authorities.contains(UserRoles.MODERATOR.getAuthority()))
            return UserRoles.MODERATOR.getAuthority();
        if (authorities.contains(UserRoles.USER.getAuthority()))
            return UserRoles.USER.getAuthority();
        return UserRoles.ANONYMOUS.getAuthority();
    }
}