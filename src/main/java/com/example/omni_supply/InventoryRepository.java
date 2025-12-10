package com.example.omni_supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    // Finds items where name contains the keyword (case insensitive)
    List<InventoryItem> findByNameContainingIgnoreCase(String keyword);
}