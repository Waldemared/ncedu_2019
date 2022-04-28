package com.ncedu.nc_edu.services.impl;

import com.ncedu.nc_edu.models.ItemCategory;
import com.ncedu.nc_edu.models.RationItem;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.repositories.RationItemRepository;
import com.ncedu.nc_edu.services.RationItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class RationItemServiceImpl implements RationItemService {
    private final RationItemRepository rationItemRepository;

    public RationItemServiceImpl(@Autowired RationItemRepository rationItemRepository) {
        this.rationItemRepository = rationItemRepository;
    }

    @Override
    public RationItem create(User user, Date date, Recipe recipe, ItemCategory category) {
        RationItem ration = new RationItem();
        ration.setOwner(user);
        ration.setDate(date);
        ration.setRecipe(recipe);
        ration.setId(UUID.randomUUID());
        ration.setCategory(category);

        return this.rationItemRepository.save(ration);
    }
}
