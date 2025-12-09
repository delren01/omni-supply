package com.example.omni_supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Inherits methods such as .save(item), .findAll(), .deleteById(id)

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {

}