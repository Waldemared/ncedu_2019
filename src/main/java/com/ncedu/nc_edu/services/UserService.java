package com.ncedu.nc_edu.services;

import com.ncedu.nc_edu.dto.resources.UserResource;
import com.ncedu.nc_edu.exceptions.AlreadyExistsException;
import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.models.Review;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(String email, String password) throws AlreadyExistsException;
    List<User> findAllUsers();

    List<Review> getReviewsById(UUID id);

    List<Recipe> getRecipesById(UUID id);

    /**
     *
     * @return User entity or exception
     * @throws EntityDoesNotExistsException if user cannot be found
     */
    User findUserById(UUID id) throws EntityDoesNotExistsException;

    /**
     *
     * @param userInfo user dto from request
     * @return User model
     * @throws EntityDoesNotExistsException throws if user with given id cannot be found
     */
    User update(UserResource userInfo) throws EntityDoesNotExistsException, AlreadyExistsException;
}
