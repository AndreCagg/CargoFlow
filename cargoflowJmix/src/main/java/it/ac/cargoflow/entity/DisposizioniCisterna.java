package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum DisposizioniCisterna implements EnumClass<Integer> {

    TT(10),
    TA(20),
    TE(30),
    TC(40);

    private final Integer id;

    DisposizioniCisterna(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static DisposizioniCisterna fromId(Integer id) {
        for (DisposizioniCisterna at : DisposizioniCisterna.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}