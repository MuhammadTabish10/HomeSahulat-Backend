package com.HomeSahulat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    private String name;
    private String password;
    private String email;
    private String phone;
    private String profilePictureUrl;
    private String deviceId;
    private String otp;
    private Boolean otpFlag;
    private String resetCode;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime resetCodeTimestamp;

    private Boolean status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}

