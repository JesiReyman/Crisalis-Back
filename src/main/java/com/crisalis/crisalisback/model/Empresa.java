package com.crisalis.crisalisback.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("empresa")
@Entity
public class Empresa extends Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /*@OneToOne
    @MapsId
    @JoinColumn(name = "persona_id")
    private Persona persona;*/

    private String razonSocial;
    private Date fechaInicio;
    private long cuit;

    
}
