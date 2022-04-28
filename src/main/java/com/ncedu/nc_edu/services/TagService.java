package com.ncedu.nc_edu.services;

import com.ncedu.nc_edu.models.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    Tag findByName(String name);
    List<Tag> findAllByNameContains(String name);

    boolean existsByName(String name);
    Tag add(String name);
}