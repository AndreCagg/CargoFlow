package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoRequisitiVeicolo implements EnumClass<Integer> {

    TRASPORTO_COLLI(10),
    TRASPORTO_RINFUSA(20),
    DISPOSIZIONI_CISTERNA(30);

    private final Integer id;

    TipoRequisitiVeicolo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoRequisitiVeicolo fromId(Integer id) {
        for (TipoRequisitiVeicolo at : TipoRequisitiVeicolo.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}