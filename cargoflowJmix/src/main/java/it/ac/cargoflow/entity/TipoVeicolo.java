package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoVeicolo implements EnumClass<Integer> {

    CASSONATO(10),
    CISTERNA_PORTACISTERNA(20),
    SFUSO(30);

    private final Integer id;

    TipoVeicolo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoVeicolo fromId(Integer id) {
        for (TipoVeicolo at : TipoVeicolo.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}