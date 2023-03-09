package com.projek.labcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projek.labcheck.entity.Produk;

public interface ProdukRepository extends JpaRepository<Produk, String>{
    
}
