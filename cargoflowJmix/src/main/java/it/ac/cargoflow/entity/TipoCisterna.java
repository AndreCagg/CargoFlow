package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoCisterna implements EnumClass<Integer> {

    LGBF(10),
    L4BH(20),
    SGAH(30),
    CXBN(40);

    private final Integer id;

    TipoCisterna(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoCisterna fromId(Integer id) {
        for (TipoCisterna at : TipoCisterna.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}