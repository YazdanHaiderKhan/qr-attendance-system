package com.yazdan.qrattendance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className;

    @Lob
    @Column(name = "qr_code_data")
    private String qrCodeData; // base64 PNG

    @Column(name = "qr_expires_at")
    private LocalDateTime qrExpiresAt;

    private Double locationLat;
    private Double locationLng;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
