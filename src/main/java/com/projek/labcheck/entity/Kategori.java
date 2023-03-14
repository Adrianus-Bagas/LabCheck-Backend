package com.projek.labcheck.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Kategori implements Serializable {
    
    @Id
    private String id;
    private String nama_kategori;
}
