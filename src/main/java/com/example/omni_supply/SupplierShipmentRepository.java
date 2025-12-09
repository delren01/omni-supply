package com.example.omni_supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierShipmentRepository extends JpaRepository<SupplierShipment, Long> {
}