package com.projek.labcheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projek.labcheck.entity.Produk;
import com.projek.labcheck.service.ProdukService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class ProdukController {
    
    @Autowired
    private ProdukService produkService;

    @GetMapping("/produk")
    public List<Produk> findAll(){
        return produkService.findAll();
    }

    @GetMapping("/produk/{id_produk}")
    public Produk findById(@PathVariable("id_produk") String id_produk){
        return produkService.findById(id_produk);
    }

    @PostMapping("/produk")
    public Produk create(@RequestBody Produk produk){
        return produkService.create(produk);
    }

    @PutMapping("/produk")
    public Produk edit(@RequestBody Produk produk){
        return produkService.edit(produk);
    }

    @DeleteMapping("/produk/{id_produk}")
    public void deleteById(@PathVariable("id_produk") String id_produk){
        produkService.deleteById(id_produk);
    }
}
