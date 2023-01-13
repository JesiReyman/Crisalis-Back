package com.crisalis.crisalisback.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("empresa")
@Builder
@Entity
public class EmpresaCliente extends Cliente{
    //@JsonBackReference
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private PersonaCliente personaCliente;

    @NotNull
    @Column(unique = true)
    private String razonSocial;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaInicio;

    @NotNull
    @Column(unique = true)
    private long cuit;

}
