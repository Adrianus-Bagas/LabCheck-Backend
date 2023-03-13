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

import com.projek.labcheck.entity.Pengguna;
import com.projek.labcheck.service.PenggunaService;

@RestController
public class PenggunaController {
    
    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/pengguna")
    public List<Pengguna> findAll(){
        return penggunaService.findAll();
    }

    @GetMapping("/pengguna/{id_pengguna}")
    public Pengguna findById(@PathVariable("id_pengguna") String id_pengguna){
        return penggunaService.findById(id_pengguna);
    }

    @PostMapping("/pengguna")
    public Pengguna create(@RequestBody Pengguna pengguna){
        return penggunaService.create(pengguna);
    }

    @PutMapping("/pengguna")
    public Pengguna edit(@RequestBody Pengguna pengguna){
        return penggunaService.edit(pengguna);
    }

    @DeleteMapping("/pengguna/{id_pengguna}")
    public void delete(@PathVariable("id_pengguna") String id_pengguna){
        penggunaService.deleteById(id_pengguna);
    }
}
