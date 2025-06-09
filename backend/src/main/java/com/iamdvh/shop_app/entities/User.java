package com.iamdvh.shop_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User extends Base implements UserDetails {
    @Column(name = "fullname", length = 100)
    String fullName;
    @Column(name = "phone_number", nullable = false, length = 10)
    String phoneNumber;
    @Column(length = 200)
    String address;
    @Column(length = 100)
    String password;
    @Column(name = "is_active")
    boolean active;
    @Column(name = "date_of_birth")
    LocalDateTime dateOfBirth;
    @Column(name = "facebook_account_id")
    Integer facebookAccountId;
    @Column(name = "google_account_id")
    Integer googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName()));
        return  authorities;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
