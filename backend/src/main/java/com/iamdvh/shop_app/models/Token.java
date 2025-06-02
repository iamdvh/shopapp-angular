package com.iamdvh.shop_app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Token extends BasePrimaryKey {
    @Column(length = 255, nullable = false)
    String token;
    @Column(name = "token_type", length = 50, nullable = false)
    String tokenType;
    @Column(name = "expiration_time")
    LocalDateTime expirationTime;
    boolean revoked;
    boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
