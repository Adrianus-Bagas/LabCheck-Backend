package com.projek.labcheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projek.labcheck.entity.Pesanan;
import com.projek.labcheck.model.PesananRequest;
import com.projek.labcheck.model.PesananResponse;
import com.projek.labcheck.security.service.UserDetailsImpl;
import com.projek.labcheck.service.PesananService;


@RequestMapping("/api")
@RestController
@PreAuthorize("isAuthenticated()")
public class PesananController {

    @Autowired
    private PesananService pesananService;

    @PostMapping("/pesanan")
    @PreAuthorize("hasAuthority('customer')")
    public PesananResponse create(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody PesananRequest request) {
        return pesananService.create(user.getUsername(), request);
    }

    @PatchMapping("/pesanan/{id_pesanan}/cancel")
    @PreAuthorize("hasAuthority('customer')")
    public Pesanan cancelPesanan(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("id_pesanan") String id_pesanan) {
        return pesananService.cancelPesanan(user.getUsername(), id_pesanan);
    }

    @PatchMapping("/pesanan/{id_pesanan}/terima")
    @PreAuthorize("hasAuthority('customer')")
    public Pesanan terimaPesanan(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("id_pesanan") String id_pesanan) {
        return pesananService.terimaPesanan(user.getUsername(), id_pesanan);
    }

    @PatchMapping("/pesanan/{id_pesanan}/konfirmasi")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan konfirmasiPesanan(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("id_pesanan") String id_pesanan) {
        return pesananService.konfirmasiPembayaran(user.getUsername(), id_pesanan);
    }

    @PatchMapping("/pesanan/{id_pesanan}/pengambilan-sampel")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan pengambilanSampel(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("id_pesanan") String id_pesanan) {
        return pesananService.pengambilanSampel(user.getUsername(), id_pesanan);
    }

    @PatchMapping("/pesanan/{id_pesanan}/uji-sampel")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan ujiSampel(@AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable("id_pesanan") String id_pesanan) {
        return pesananService.ujiSampel(user.getUsername(), id_pesanan);
    }

    @GetMapping("/pesanan")
    @PreAuthorize("hasAuthority('customer')")
    public List<Pesanan> findAllPesananUser(@AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {
        return pesananService.findAllPesananUser(user.getUsername(), page, limit);
    }

    @GetMapping("/pesanan/admin")
    @PreAuthorize("hasAuthority('admin')")
    public List<Pesanan> search(@AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {
        return pesananService.search(filterText, page, limit);
    }

}
