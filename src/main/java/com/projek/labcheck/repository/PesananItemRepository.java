package com.projek.labcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projek.labcheck.entity.PesananItem;

public interface PesananItemRepository extends JpaRepository<PesananItem, String>{
    
}
