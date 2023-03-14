package com.projek.labcheck.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PesananRequest implements Serializable {
    
    private BigDecimal ongkir;
    private String alamat_tes;
    private List<KeranjangRequest> items;

}
