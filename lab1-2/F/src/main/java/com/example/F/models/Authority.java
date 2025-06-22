package com.example.F.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}