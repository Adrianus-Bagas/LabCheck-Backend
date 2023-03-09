package com.projek.labcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projek.labcheck.entity.Kategori;

public interface KategoriRepository extends JpaRepository<Kategori, String>{
    
}
