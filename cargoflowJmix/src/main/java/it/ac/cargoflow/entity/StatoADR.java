package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum StatoADR implements EnumClass<Integer> {

    VALIDO(10),
    NON_VALIDO(20);

    private final Integer id;

    StatoADR(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static StatoADR fromId(Integer id) {
        for (StatoADR at : StatoADR.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}