package com.projek.labcheck.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.projek.labcheck.entity.Produk;
import com.projek.labcheck.exception.BadRequestException;
import com.projek.labcheck.exception.ResourceNotFoundException;
import com.projek.labcheck.repository.KategoriRepository;
import com.projek.labcheck.repository.ProdukRepository;

@Service
public class ProdukService {
    
    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private KategoriRepository kategoriRepository;

    public List<Produk> findAll(){
        return produkRepository.findAll();
    }

    public Produk findById(String id_produk){
        return produkRepository.findById(id_produk).orElseThrow(()->new ResourceNotFoundException("Produk dengan id "+id_produk+" tidak ditemukan"));
    }

    public Produk create(Produk produk){
        if(!StringUtils.hasText(produk.getNama_produk())){
            throw new BadRequestException("Nama Produk tidak boleh kosong");
        }

        if(produk.getKategori()==null){
            throw new BadRequestException("Kategori tidak boleh kosong");
        }
        
        if(!StringUtils.hasText(produk.getKategori().getId())){
            throw new BadRequestException("Kategori ID tidak boleh kosong");
        }

        kategoriRepository.findById(produk.getKategori().getId())
        .orElseThrow(()->new BadRequestException("Kategori ID "+produk.getKategori().getId()+" tidak ditemukan"));

        produk.setId(UUID.randomUUID().toString());
        return produkRepository.save(produk);
    }

    public Produk edit(Produk produk){

        if(!StringUtils.hasText(produk.getId())){
            throw new BadRequestException("Produk ID tidak boleh kosong");
        }

        if(!StringUtils.hasText(produk.getNama_produk())){
            throw new BadRequestException("Nama Produk tidak boleh kosong");
        }

        if(produk.getKategori()==null){
            throw new BadRequestException("Kategori tidak boleh kosong");
        }
        
        if(!StringUtils.hasText(produk.getKategori().getId())){
            throw new BadRequestException("Kategori ID tidak boleh kosong");
        }

        kategoriRepository.findById(produk.getKategori().getId())
        .orElseThrow(()->new BadRequestException("Kategori ID "+produk.getKategori().getId()+" tidak ditemukan"));

        return produkRepository.save(produk);
    }

    public void deleteById(String id_produk){
        produkRepository.deleteById(id_produk);
    }
}
