package com.projek.labcheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.projek.labcheck.model.StatusPesanan;

import lombok.Data;

@Data
@Entity
public class Pesanan implements Serializable {
    
    @Id
    private String id;
    private String nomor_pesanan;
    @Temporal(TemporalType.DATE)
    private Date tanggal;
    @JoinColumn
    @ManyToOne
    private Pengguna pengguna;
    private String alamat_tes;
    private BigDecimal jumlah;
    private BigDecimal ongkir;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private StatusPesanan status_pesanan;
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuPesan;
}
