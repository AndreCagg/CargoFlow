package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoImballaggio implements EnumClass<Integer> {

    COMBINATO(10),
    TERMO(20);

    private final Integer id;

    TipoImballaggio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoImballaggio fromId(Integer id) {
        for (TipoImballaggio at : TipoImballaggio.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}