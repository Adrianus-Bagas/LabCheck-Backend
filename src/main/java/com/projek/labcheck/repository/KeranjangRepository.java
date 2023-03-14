package com.projek.labcheck.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projek.labcheck.entity.Keranjang;

public interface KeranjangRepository extends JpaRepository<Keranjang, String>{

    Optional<Keranjang> findByPenggunaIdAndProdukId(String user_name, String id_produk);

    List<Keranjang> findByPenggunaId(String user_name);
    
}
