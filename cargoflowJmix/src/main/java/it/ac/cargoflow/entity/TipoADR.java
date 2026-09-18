package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoADR implements EnumClass<Integer> {

    COLLI(10),
    RINFUSA(20),
    CISTERNA(30);

    private final Integer id;

    TipoADR(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoADR fromId(Integer id) {
        for (TipoADR at : TipoADR.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}