package com.projek.labcheck.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User implements Serializable {
    
    @Id
    private String id_user;
    private String password;
    private String nama;
    private String alamat;
    private String email;
    private String hp;
    private String role;
    private Boolean is_aktif;
}
