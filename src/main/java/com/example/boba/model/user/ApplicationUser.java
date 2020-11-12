package com.example.boba.model.user;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jackson2.CoreJackson2Module;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("applicationUser")
public class ApplicationUser implements UserDetails {

    @Id
    private String id;

    private String name;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Date dateOfBirth;

    @Field
    private Role role = Role.CUSTOMER;

    private Boolean isConfirmed = false;

    public enum Role {CUSTOMER, STAFF, ADMIN, BANNED}

    public ApplicationUser(String name, String email, String password, Date dateOfBirth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.toString()));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }

    @JsonSetter("username")
    @JsonAlias("email")
    public void setEmail(String email) {
        this.email = email;
    }
}
