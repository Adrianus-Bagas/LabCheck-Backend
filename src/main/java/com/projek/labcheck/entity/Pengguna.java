package com.projek.labcheck.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pengguna implements Serializable {
    
    
    @Id
    private String id;
    @JsonIgnore
    private String password;
    private String nama;
    @JsonIgnore
    private String alamat;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String hp;
    private String role;
    @JsonIgnore
    private Boolean is_aktif;

    public Pengguna(String username) {
        this.id = username;
    }
}
