package com.projek.labcheck.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projek.labcheck.entity.Produk;
import com.projek.labcheck.exception.ResourceNotFoundException;
import com.projek.labcheck.repository.ProdukRepository;

@Service
public class ProdukService {
    
    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> findAll(){
        return produkRepository.findAll();
    }

    public Produk findById(String id_produk){
        return produkRepository.findById(id_produk).orElseThrow(()->new ResourceNotFoundException("Produk dengan id "+id_produk+" tidak ditemukan"));
    }

    public Produk create(Produk produk){
        produk.setId_produk(UUID.randomUUID().toString());
        return produkRepository.save(produk);
    }

    public Produk edit(Produk produk){
        return produkRepository.save(produk);
    }

    public void deleteById(String id_produk){
        produkRepository.deleteById(id_produk);
    }
}
