package com.ncedu.nc_edu.repositories;

import com.ncedu.nc_edu.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
    Page<Tag> findAllByNameContaining(String name, Pageable pageable);
}
