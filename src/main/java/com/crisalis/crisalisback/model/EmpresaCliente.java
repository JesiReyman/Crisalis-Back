package com.crisalis.crisalisback.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("empresa")
@Entity
public class EmpresaCliente{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "empresaCliente", cascade = CascadeType.ALL)
    private List<Pedido> listaDePedidos = new ArrayList<Pedido>();

    @Column(unique = true)
    private String razonSocial;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaInicio;
    @Column(unique = true)
    private long cuit;

    
}
