package com.bm.transfer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 600)
    private String token;

    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "user-id", nullable = false)
    private User user;

    private boolean invalidated;
}
