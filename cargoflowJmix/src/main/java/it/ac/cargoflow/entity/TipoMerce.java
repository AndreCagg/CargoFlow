package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoMerce implements EnumClass<Integer> {

    COLLO(10),
    PALLET(20);

    private final Integer id;

    TipoMerce(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoMerce fromId(Integer id) {
        for (TipoMerce at : TipoMerce.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}