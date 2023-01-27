package com.crisalis.crisalisback.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum AplicaImpuesto {
    NINGUNO,
    PRODUCTOS,
    SERVICIOS,
    TODOS;

    /*private String valor;

    @JsonValue
    public String getValor() {
        return valor;
    }

    AplicaImpuesto(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }

    @JsonCreator
    public static AplicaImpuesto forValue(String valor) {
        return Stream.of(AplicaImpuesto.values())
                .filter(enumValue -> enumValue.name().equals(valor.toUpperCase()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }*/
}
