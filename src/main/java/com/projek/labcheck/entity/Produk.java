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
public class Produk implements Serializable {
    
    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Kategori kategori;
    private String nama_produk;
    private BigDecimal harga;
    private int stok;
}
