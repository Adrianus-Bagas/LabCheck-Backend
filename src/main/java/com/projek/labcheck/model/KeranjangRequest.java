package com.projek.labcheck.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeranjangRequest implements Serializable{
    
    private String id_produk;
    private int kuantitas;
}
