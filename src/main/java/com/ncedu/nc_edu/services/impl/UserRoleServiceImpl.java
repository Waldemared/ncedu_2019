package com.ncedu.nc_edu.services.impl;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.UserRole;
import com.ncedu.nc_edu.repositories.UserRoleRepository;
import com.ncedu.nc_edu.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(@Autowired UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Set<UserRole> findRolesByUserId(UUID id) {
        return userRoleRepository.findByUserId(id);
    }

    @Override
    public UserRole findById(UUID id) throws EntityDoesNotExistsException {
        return userRoleRepository.findById(id).orElseThrow(() -> new EntityDoesNotExistsException("Role"));
    }

    @Override
    public UserRole findByRole(String role) throws EntityDoesNotExistsException {
        return userRoleRepository.findByRole(role).orElseThrow(() -> new EntityDoesNotExistsException("Role"));
    }
}
