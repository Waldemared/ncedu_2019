package com.ncedu.nc_edu.services;


import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.UserRole;

import java.util.Set;
import java.util.UUID;

public interface UserRoleService {
    Set<UserRole> findRolesByUserId(UUID id);
    UserRole findById(UUID id) throws EntityDoesNotExistsException;
    UserRole findByRole(String name) throws EntityDoesNotExistsException;
}
