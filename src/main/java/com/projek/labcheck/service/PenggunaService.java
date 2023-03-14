package com.projek.labcheck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.projek.labcheck.entity.Pengguna;
import com.projek.labcheck.exception.BadRequestException;
import com.projek.labcheck.exception.ResourceNotFoundException;
import com.projek.labcheck.repository.PenggunaRepository;

@Service
public class PenggunaService {

    @Autowired
    private PenggunaRepository penggunaRepository;

    public Pengguna findById(String id_pengguna) {
        return penggunaRepository.findById(id_pengguna)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pengguna dengan id " + id_pengguna + " tidak ditemukan"));
    }

    public List<Pengguna> findAll() {
        return penggunaRepository.findAll();
    }

    public Pengguna create(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus diisi");
        }

        if (penggunaRepository.existsById(pengguna.getId())) {
            throw new BadRequestException("Username " + pengguna.getId() + " sudah terdaftar");
        }

        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }

        if (penggunaRepository.existsByEmail(pengguna.getEmail())) {
            throw new BadRequestException("Email " + pengguna.getEmail() + " sudah terdaftar");
        }

        pengguna.setIs_aktif(true);

        return penggunaRepository.save(pengguna);
    }

    public Pengguna edit(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus diisi");
        }

        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }
        return penggunaRepository.save(pengguna);
    }

    public void deleteById(String id_pengguna) {
        penggunaRepository.deleteById(id_pengguna);
    }
}
