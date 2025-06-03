package com.iamdvh.shop_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SocialAccount extends BasePrimaryKey{
    @Column(name = "token_type", length = 50, nullable = false)
    String tokenType;
    @Column(name = "provider_id", length = 50, nullable = false)
    String providerId;
    @Column(length = 150)
    String email;
    @Column(length = 100)
    String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
