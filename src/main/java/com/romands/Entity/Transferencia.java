package com.romands.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "transferencias")
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "loja_id")
    private User loja;
    @OneToMany(mappedBy = "transferencia", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemPedido> produtos;

    @Enumerated(EnumType.STRING)
    private TransferenciaStatus status;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
