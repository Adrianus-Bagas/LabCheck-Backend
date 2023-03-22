package com.projek.labcheck.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.projek.labcheck.entity.Pesanan;
import com.projek.labcheck.entity.PesananItem;

import lombok.Data;

@Data
public class PesananResponse implements Serializable {
    
    private String id;
    private String nomor_pesanan;
    private Date tanggal;
    private String namaPelanggan;
    private String alamat_tes;
    private Date waktuDipesan;
    private BigDecimal jumlah;
    private BigDecimal ongkir;
    private BigDecimal total;
    private List<PesananResponse.Item> items;

    public PesananResponse(Pesanan pesanan, List<PesananItem> pesananItems){
        this.id = pesanan.getId();
        this.nomor_pesanan = pesanan.getNomor_pesanan();
        this.tanggal = pesanan.getTanggal();
        this.namaPelanggan = pesanan.getPengguna().getNama();
        this.alamat_tes = pesanan.getAlamat_tes();
        this.waktuDipesan = pesanan.getWaktuPesan();
        this.jumlah = pesanan.getJumlah();
        this.ongkir = pesanan.getOngkir();
        this.total = pesanan.getTotal();
        items = new ArrayList<>();

        for (PesananItem pesananItem : pesananItems) {
            Item item = new Item();
            item.setId_produk(pesananItem.getProduk().getId());
            item.setNama_produk(pesananItem.getKeterangan());
            item.setKuantitas(pesananItem.getKuantitas());
            item.setHarga(pesananItem.getHarga());
            item.setJumlah(pesananItem.getJumlah());
            items.add(item);
        }
    }

    @Data
    public static class Item implements Serializable{
        private String id_produk;
        private String nama_produk;
        private int kuantitas;
        private BigDecimal harga;
        private BigDecimal jumlah;
    }

}
