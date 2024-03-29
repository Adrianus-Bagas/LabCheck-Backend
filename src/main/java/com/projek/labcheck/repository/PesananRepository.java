package com.projek.labcheck.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projek.labcheck.entity.Pesanan;

public interface PesananRepository extends JpaRepository<Pesanan, String>{

    List<Pesanan> findByPenggunaId(String username, Pageable pageable);

    @Query("SELECT p from Pesanan p where LOWER(p.nomor_pesanan) LIKE %:filterText% OR LOWER(p.pengguna.nama) LIKE %:filterText%")
    List<Pesanan> search(@Param("filterText") String filterText, Pageable pageable);
    
}
