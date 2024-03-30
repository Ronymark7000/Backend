package com.project.JewelHub.datascapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetalPriceRepo extends JpaRepository<MetalPrice, Integer> {
    @Query(value = "SELECT * FROM (SELECT * FROM metal_price m ORDER BY m.price_date DESC) LIMIT 1", nativeQuery = true)
    Optional<MetalPrice> findLatestMetalPrice();
}
