package com.projek.labcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projek.labcheck.entity.Pengguna;

public interface PenggunaRepository extends JpaRepository<Pengguna, String>{

    boolean existsByEmail(String email);
    
}
