package com.projek.labcheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projek.labcheck.entity.Keranjang;
import com.projek.labcheck.model.KeranjangRequest;
import com.projek.labcheck.security.service.UserDetailsImpl;
import com.projek.labcheck.service.KeranjangService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class KeranjangController {
    
    @Autowired
    private KeranjangService keranjangService;

    @GetMapping("/keranjang")
    public List<Keranjang> findByPenggunaId(@AuthenticationPrincipal UserDetailsImpl user){
        return keranjangService.findByIdPengguna(user.getUsername());
    }

    @PostMapping("/keranjang")
    public Keranjang create(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody KeranjangRequest request){
        return keranjangService.create(user.getUsername(), request.getId_produk(), request.getKuantitas());
    }

    @PatchMapping("/keranjang/{id_produk}")
    public Keranjang updateKuantitas(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("id_produk") String id_produk, @RequestParam("kuantitas") int kuantitas){
        return keranjangService.updateKuantitas(user.getUsername(), id_produk, kuantitas);
    }

    @DeleteMapping("/keranjang/{id_produk}")
    public void delete(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("id_produk") String id_produk){
        keranjangService.delete(user.getUsername(), id_produk);
    }

}
