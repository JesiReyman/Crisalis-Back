package com.crisalis.crisalisback.model;

import com.crisalis.crisalisback.enums.AplicaAdicional;
import com.crisalis.crisalisback.enums.TipoAdicional;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Getter @Setter
@NoArgsConstructor
@Entity
public class Adicional {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(precision = 11, scale = 4)
    private BigDecimal porcentaje;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AplicaAdicional aplica;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoAdicional tipo;

    @Builder
    public Adicional(String nombre, BigDecimal porcentaje, AplicaAdicional aplica, TipoAdicional tipo) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.aplica = aplica;
        this.tipo = tipo;
    }
}
