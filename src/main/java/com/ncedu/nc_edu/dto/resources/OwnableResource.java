package com.ncedu.nc_edu.dto.resources;

import com.ncedu.nc_edu.models.User;

import java.util.UUID;

public interface OwnableResource {
    UUID getOwnerId();
}
