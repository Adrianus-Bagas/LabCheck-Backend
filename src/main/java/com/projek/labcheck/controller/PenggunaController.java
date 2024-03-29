package com.projek.labcheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projek.labcheck.entity.Pengguna;
import com.projek.labcheck.model.SignupRequest;
import com.projek.labcheck.service.PenggunaService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class PenggunaController {
    
    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/pengguna")
    public List<Pengguna> findAll(){
        return penggunaService.findAll();
    }

    @GetMapping("/pengguna/{id_pengguna}")
    public Pengguna findById(@PathVariable("id_pengguna") String id_pengguna){
        return penggunaService.findById(id_pengguna);
    }

    @PostMapping("/pengguna")
    @PreAuthorize("hasAuthority('admin')")
    public Pengguna create(@RequestBody SignupRequest signupRequest){
        Pengguna pengguna = new Pengguna();
        pengguna.setId(signupRequest.getUsername());
        pengguna.setEmail(signupRequest.getEmail());
        pengguna.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        pengguna.setNama(signupRequest.getNama());
        pengguna.setRole("admin");
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
