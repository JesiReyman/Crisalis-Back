package com.crisalis.crisalisback.model;

import com.crisalis.crisalisback.enums.AplicaImpuesto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Getter @Setter
@NoArgsConstructor
@Entity
public class Impuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(precision = 11, scale = 4)
    private BigDecimal porcentaje;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AplicaImpuesto aplica;
    @Builder

    public Impuesto(String nombre, BigDecimal porcentaje, AplicaImpuesto aplica) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.aplica = aplica;
    }
}
