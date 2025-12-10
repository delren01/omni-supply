package com.example.omni_supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByNameContainingIgnoreCase(String keyword);
    @Query("SELECT i FROM InventoryItem i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.sku) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<InventoryItem> searchEverything(@Param("keyword") String keyword);
}