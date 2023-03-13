package com.projek.labcheck.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Pengguna implements Serializable {
    
    @Id
    private String user_name;
    @JsonIgnore
    private String password;
    private String nama;
    private String alamat;
    private String email;
    private String hp;
    private String role;
    private Boolean is_aktif;
}
