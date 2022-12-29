package com.crisalis.crisalisback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("personaFisica")
@Entity
public class Cliente extends Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @NotNull
    private long dni;

    @JsonManagedReference
    @OneToOne(mappedBy="cliente", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private EmpresaCliente empresa;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> listaDePedidos = new ArrayList<Pedido>();

    public Cliente(String nombre, String apellido, long dni) {
        super(nombre, apellido);
        this.dni = dni;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente){
        this.empresa = empresaCliente;
        this.empresa.setCliente(this);
    }
}
