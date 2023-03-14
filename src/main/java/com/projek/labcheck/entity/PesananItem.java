package com.projek.labcheck.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class PesananItem implements Serializable {
    
    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Pesanan pesanan;
    @JoinColumn
    @ManyToOne
    private Produk produk;
    private String keterangan;
    private int kuantitas;
    private BigDecimal harga;
    private BigDecimal jumlah;
}
