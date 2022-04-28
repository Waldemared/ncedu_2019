package com.ncedu.nc_edu.services;

import com.ncedu.nc_edu.models.ItemCategory;
import com.ncedu.nc_edu.models.RationItem;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.User;

import java.util.Date;

public interface RationItemService {

    RationItem create (User user, Date date, Recipe recipe, ItemCategory category);
}
