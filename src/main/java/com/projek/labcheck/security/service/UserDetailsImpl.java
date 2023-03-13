package com.projek.labcheck.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projek.labcheck.entity.Pengguna;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String email;
    private String nama;

    public UserDetailsImpl(String username, String email, String nama, String password, String role) {
        this.username = username;
        this.email = email;
        this.nama = nama;
        this.password = password;
        this.role = role;
    }

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String role;

    public static UserDetailsImpl build(Pengguna pengguna){
        return new UserDetailsImpl(pengguna.getUser_name(), pengguna.getEmail(), pengguna.getNama(), pengguna.getPassword(), pengguna.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(StringUtils.hasText(role)){
            String[] split_role = role.replaceAll(" ", "").split(",");
            for (String i : split_role) {
                authorities.add(new SimpleGrantedAuthority(i));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
