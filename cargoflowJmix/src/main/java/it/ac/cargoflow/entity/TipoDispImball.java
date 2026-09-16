package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum TipoDispImball implements EnumClass<Integer> {

    PP(10),
    B(20),
    L(30);

    private final Integer id;

    TipoDispImball(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TipoDispImball fromId(Integer id) {
        for (TipoDispImball at : TipoDispImball.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}