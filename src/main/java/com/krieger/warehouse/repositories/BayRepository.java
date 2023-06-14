package com.krieger.warehouse.repositories;

import com.krieger.warehouse.models.Bay;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BayRepository extends JpaRepository<Bay, Long> {
}
