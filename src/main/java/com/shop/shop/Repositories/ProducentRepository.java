package com.shop.shop.Repositories;


import com.shop.shop.Entity.Producent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducentRepository extends JpaRepository<Producent, Integer> {
}
