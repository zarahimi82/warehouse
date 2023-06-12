package com.krieger.warehouse.repositories;

import com.krieger.warehouse.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehaousRepository extends JpaRepository<Warehouse, Long>  {
}
