package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoIstrImballaggio implements EnumClass<Integer> {

    P(10),
    IBC(20),
    LP(30);

    private final Integer id;

    TipoIstrImballaggio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoIstrImballaggio fromId(Integer id) {
        for (TipoIstrImballaggio at : TipoIstrImballaggio.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}