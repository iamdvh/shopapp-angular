package com.iamdvh.shop_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User extends Base{
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
}
