package com.ncedu.nc_edu.repositories;

import com.ncedu.nc_edu.models.RationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RationItemRepository extends JpaRepository<RationItem, UUID> {
}
