package com.projek.labcheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projek.labcheck.entity.Kategori;
import com.projek.labcheck.service.KategoriService;

@RestController
public class KategoriController {
    
    @Autowired
    private KategoriService kategoriService;

    @GetMapping("/kategori")
    public List<Kategori> findAll(){
        return kategoriService.findAll();
    }

    @GetMapping("/kategori/{id_kategori}")
    public Kategori findById(@PathVariable("id_kategori") String id_kategori){
        return kategoriService.findById(id_kategori);
    }

    @PostMapping("/kategori")
    public Kategori create(@RequestBody Kategori kategori){
        return kategoriService.create(kategori);
    }

    @PutMapping("/kategori")
    public Kategori edit(@RequestBody Kategori kategori){
        return kategoriService.edit(kategori);
    }

    @DeleteMapping("/kategori/{id_kategori}")
    public void delete(@PathVariable("id_kategori") String id_kategori){
        kategoriService.deleteById(id_kategori);
    }
}
