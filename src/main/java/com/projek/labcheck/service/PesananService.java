package com.projek.labcheck.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projek.labcheck.entity.Pengguna;
import com.projek.labcheck.entity.Pesanan;
import com.projek.labcheck.entity.PesananItem;
import com.projek.labcheck.entity.Produk;
import com.projek.labcheck.exception.BadRequestException;
import com.projek.labcheck.exception.ResourceNotFoundException;
import com.projek.labcheck.model.KeranjangRequest;
import com.projek.labcheck.model.PesananRequest;
import com.projek.labcheck.model.PesananResponse;
import com.projek.labcheck.model.StatusPesanan;
import com.projek.labcheck.repository.PesananItemRepository;
import com.projek.labcheck.repository.PesananRepository;
import com.projek.labcheck.repository.ProdukRepository;

@Service
public class PesananService {

    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PesananItemRepository pesananItemRepository;

    @Autowired
    private KeranjangService keranjangService;

    @Autowired
    private PesananLogService pesananLogService;

    @Transactional
    public PesananResponse create(String username, PesananRequest request) {
        Pesanan pesanan = new Pesanan();
        pesanan.setId(UUID.randomUUID().toString());
        pesanan.setTanggal(new Date());
        pesanan.setNomor_pesanan(generateNomorPesanan());
        pesanan.setPengguna(new Pengguna(username));
        pesanan.setAlamat_tes(request.getAlamat_tes());
        pesanan.setStatus_pesanan(StatusPesanan.DRAFT);
        pesanan.setWaktuPesan(new Date());

        List<PesananItem> items = new ArrayList<>();
        for (KeranjangRequest keranjang : request.getItems()) {
            Produk produk = produkRepository.findById(keranjang.getId_produk())
                    .orElseThrow(() -> new BadRequestException(
                            "Produk ID " + keranjang.getId_produk() + " tidak ditemukan"));

            if (produk.getStok() < keranjang.getKuantitas()) {
                throw new BadRequestException("Stok tidak mencukupi");
            }

            PesananItem pesananItem = new PesananItem();
            pesananItem.setId(UUID.randomUUID().toString());
            pesananItem.setProduk(produk);
            pesananItem.setKeterangan(produk.getNama_produk());
            pesananItem.setKuantitas(keranjang.getKuantitas());
            pesananItem.setHarga(produk.getHarga());
            pesananItem.setJumlah(new BigDecimal(pesananItem.getHarga().doubleValue() * pesananItem.getKuantitas()));
            pesananItem.setPesanan(pesanan);
            items.add(pesananItem);
        }

        BigDecimal jumlah = BigDecimal.ZERO;
        for (PesananItem pesananItem : items) {
            jumlah = jumlah.add(pesananItem.getJumlah());
        }
        pesanan.setJumlah(jumlah);
        pesanan.setOngkir(request.getOngkir());
        pesanan.setTotal(pesanan.getJumlah().add(pesanan.getOngkir()));

        Pesanan createdPesanan = pesananRepository.save(pesanan);

        for (PesananItem pesananItem : items) {
            pesananItemRepository.save(pesananItem);
            Produk produk = pesananItem.getProduk();
            produk.setStok(produk.getStok() - pesananItem.getKuantitas());
            produkRepository.save(produk);
            keranjangService.delete(username, produk.getId());
        }

        // catat log
        pesananLogService.createLog(username, createdPesanan, PesananLogService.DRAFT, "Pesanan dibuat");

        PesananResponse pesananResponse = new PesananResponse(createdPesanan, items);
        return pesananResponse;
    }

    @Transactional
    public Pesanan cancelPesanan(String id_pesanan, String username) {
        Pesanan pesanan = pesananRepository.findById(id_pesanan)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan id " + id_pesanan + " tidak ditemukan"));

        if (!username.equals(pesanan.getPengguna().getId())) {
            throw new BadRequestException("Pesanan hanya dapat dihapus oleh yang bersangkutan");
        }

        if (!StatusPesanan.DRAFT.equals(pesanan.getStatus_pesanan())) {
            throw new BadRequestException("Pesanan ini tidak dapat dibatalkan karena sudah diproses");
        }

        pesanan.setStatus_pesanan(StatusPesanan.DIBATALKAN);
        Pesanan canceledPesanan = pesananRepository.save(pesanan);
        pesananLogService.createLog(username, canceledPesanan, PesananLogService.DIBATALKAN, "Pesanan dibatalkan");
        return canceledPesanan;
    }

    @Transactional
    public Pesanan terimaPesanan(String id_pesanan, String username) {
        Pesanan pesanan = pesananRepository.findById(id_pesanan)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan id " + id_pesanan + " tidak ditemukan"));

        if (!username.equals(pesanan.getPengguna().getId())) {
            throw new BadRequestException("Pesanan hanya dapat diterima oleh yang bersangkutan");
        }

        if (!StatusPesanan.UJI_SAMPEL.equals(pesanan.getStatus_pesanan())) {
            throw new BadRequestException(
                    "Penerimaan gagal, status pesanan saat ini adalah " + pesanan.getStatus_pesanan());
        }

        pesanan.setStatus_pesanan(StatusPesanan.SELESAI);
        Pesanan receivedPesanan = pesananRepository.save(pesanan);
        pesananLogService.createLog(username, receivedPesanan, PesananLogService.SELESAI, "Pesanan selesai");
        return receivedPesanan;
    }

    public List<Pesanan> findAllPesananUser(String username, int page, int limit) {
        return pesananRepository.findByPenggunaId(username,
                PageRequest.of(page, limit, Sort.by("waktuPesan").descending()));
    }

    public List<Pesanan> search(String filterText, int page, int limit) {
        return pesananRepository.search(filterText.toLowerCase(),
                PageRequest.of(page, limit, Sort.by("waktuPesan").descending()));
    }

    @Transactional
    public Pesanan konfirmasiPembayaran(String id_pesanan, String username) {
        Pesanan pesanan = pesananRepository.findById(id_pesanan)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan id " + id_pesanan + " tidak ditemukan"));

        if (!StatusPesanan.DRAFT.equals(pesanan.getStatus_pesanan())) {
            throw new BadRequestException(
                    "Konfirmasi gagal, status pesanan saat ini adalah " + pesanan.getStatus_pesanan());
        }

        pesanan.setStatus_pesanan(StatusPesanan.PEMBAYARAN);
        Pesanan pembayaranPesanan = pesananRepository.save(pesanan);
        pesananLogService.createLog(username, pembayaranPesanan, PesananLogService.PEMBAYARAN, "Pesanan dikonfirmasi");
        return pembayaranPesanan;
    }

    @Transactional
    public Pesanan pengambilanSampel(String id_pesanan, String username) {
        Pesanan pesanan = pesananRepository.findById(id_pesanan)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan id " + id_pesanan + " tidak ditemukan"));

        if (!StatusPesanan.PEMBAYARAN.equals(pesanan.getStatus_pesanan())) {
            throw new BadRequestException(
                    "Pengambilan Sampel gagal, status pesanan saat ini adalah " + pesanan.getStatus_pesanan());
        }

        pesanan.setStatus_pesanan(StatusPesanan.PENGAMBILAN_SAMPEL);
        Pesanan pengambilanSampelTes = pesananRepository.save(pesanan);
        pesananLogService.createLog(username, pengambilanSampelTes, PesananLogService.PENGAMBILAN_SAMPEL, "Proses Pengambilan sampel");
        return pengambilanSampelTes;
    }

    @Transactional
    public Pesanan ujiSampel(String id_pesanan, String username) {
        Pesanan pesanan = pesananRepository.findById(id_pesanan)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan id " + id_pesanan + " tidak ditemukan"));

        if (!StatusPesanan.PENGAMBILAN_SAMPEL.equals(pesanan.getStatus_pesanan())) {
            throw new BadRequestException(
                    "Uji Sampel gagal, status pesanan saat ini adalah " + pesanan.getStatus_pesanan());
        }

        pesanan.setStatus_pesanan(StatusPesanan.UJI_SAMPEL);
        Pesanan ujiSampelTes = pesananRepository.save(pesanan);
        pesananLogService.createLog(username, ujiSampelTes, PesananLogService.UJI_SAMPEL, "Proses Uji sampel");
        return ujiSampelTes;
    }

    private String generateNomorPesanan() {
        return String.format("%016d", System.nanoTime());
    }
}
