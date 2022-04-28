package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.models.RationItem;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.security.CustomUserDetails;
import com.ncedu.nc_edu.services.RationItemService;
import com.ncedu.nc_edu.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class RationItemController {
    private final RationItemService rationItemService;
    private final RecipeService recipeService;

    public RationItemController(
            @Autowired RationItemService rationItemService,
            @Autowired RecipeService recipeService
    ) {
        this.rationItemService = rationItemService;
        this.recipeService = recipeService;
    }

    @PostMapping("/ration")
    @ResponseStatus(HttpStatus.CREATED)
    public RationItem create (Authentication auth, UUID receiptId, String category) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        Recipe recipe = this.recipeService.findById(receiptId);

        return null;
    }
}
