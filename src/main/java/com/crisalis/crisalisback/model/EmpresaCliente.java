package com.crisalis.crisalisback.model;


import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
@NoArgsConstructor
@DiscriminatorValue("empresa")

@Entity
public class EmpresaCliente extends Cliente{
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private PersonaCliente personaCliente;
    private String razonSocial;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaInicio;
    @Builder
    public EmpresaCliente(String tipo, long dniOCuit, String razonSocial, LocalDate fechaInicio) {
        super(tipo, dniOCuit);
        this.razonSocial = razonSocial;
        this.fechaInicio = fechaInicio;
    }
}
