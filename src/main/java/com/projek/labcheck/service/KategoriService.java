package com.projek.labcheck.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projek.labcheck.entity.Kategori;
import com.projek.labcheck.exception.ResourceNotFoundException;
import com.projek.labcheck.repository.KategoriRepository;

@Service
public class KategoriService {
    
    @Autowired
    private KategoriRepository kategoriRepository;

    public Kategori findById(String id_kategori){
        return kategoriRepository.findById(id_kategori)
        .orElseThrow(()->new ResourceNotFoundException("Kategori dengan id "+id_kategori+" tidak ditemukan"));
    }

    public List<Kategori> findAll(){
        return kategoriRepository.findAll();
    }

    public Kategori create(Kategori kategori){
        kategori.setId_kategori(UUID.randomUUID().toString());
        return kategoriRepository.save(kategori);
    }

    public Kategori edit(Kategori kategori){
        return kategoriRepository.save(kategori);
    }

    public void deleteById(String id_kategori){
        kategoriRepository.deleteById(id_kategori);
    }
}
