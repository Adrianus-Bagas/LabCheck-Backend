package com.projek.labcheck.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projek.labcheck.entity.Keranjang;
import com.projek.labcheck.entity.Pengguna;
import com.projek.labcheck.entity.Produk;
import com.projek.labcheck.exception.BadRequestException;
import com.projek.labcheck.repository.KeranjangRepository;
import com.projek.labcheck.repository.ProdukRepository;

@Service
public class KeranjangService {
    
    @Autowired
    private ProdukRepository produkRepository;
    @Autowired
    private KeranjangRepository keranjangRepository;

    @Transactional
    public Keranjang create(String username, String id_produk, int kuantitas){

        // 1 .cek apakah produk ada dalam database
        Produk produk = produkRepository.findById(id_produk)
                        .orElseThrow(()-> new BadRequestException("Produk id "+id_produk+" tidak ditemukan"));

        // 2. apakah barang sudah ada pada keranjang milik customer
        Optional<Keranjang> optional = keranjangRepository.findByPenggunaIdAndProdukId(username, id_produk);

        // 3. jika sudah, maka update kuantitas dan hitung ulang
        Keranjang keranjang;
        if(optional.isPresent()){
            keranjang = optional.get();
            keranjang.setKuantitas(keranjang.getKuantitas()+kuantitas);
            keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue()*keranjang.getKuantitas()));
            keranjangRepository.save(keranjang);
        }else{
        // 4. jika belum, maka buat keranjang baru
            keranjang = new Keranjang();
            keranjang.setId(UUID.randomUUID().toString());
            keranjang.setProduk(produk);
            keranjang.setKuantitas(kuantitas);
            keranjang.setHarga(produk.getHarga());
            keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue()*keranjang.getKuantitas()));
            keranjang.setPengguna(new Pengguna(username));
            keranjangRepository.save(keranjang);
        }
        return keranjang;
    }

    @Transactional
    public Keranjang updateKuantitas(String username, String id_produk, int kuantitas){

        Keranjang keranjang = keranjangRepository.findByPenggunaIdAndProdukId(username, id_produk)
                                .orElseThrow(()-> new BadRequestException("Produk id "+id_produk+" tidak ditemukan dalam keranjang anda"));
        keranjang.setKuantitas(kuantitas);
        keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue()*keranjang.getKuantitas()));
        keranjangRepository.save(keranjang);
        return keranjang;
                    
    }

    @Transactional
    public void delete(String username, String id_produk){

        Keranjang keranjang = keranjangRepository.findByPenggunaIdAndProdukId(username, id_produk)
                                .orElseThrow(()-> new BadRequestException("Produk id "+id_produk+" tidak ditemukan dalam keranjang anda"));
        keranjangRepository.delete(keranjang);        
    }

    public List<Keranjang> findByIdPengguna(String username){
        return keranjangRepository.findByPenggunaId(username);
    }


}
