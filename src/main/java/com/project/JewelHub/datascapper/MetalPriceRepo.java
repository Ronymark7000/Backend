package com.project.JewelHub.datascapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetalPriceRepo extends JpaRepository<MetalPrice, Integer> {
}
