package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum GruppoImballaggio implements EnumClass<Integer> {

    I(10),
    II(20),
    III(30);

    private final Integer id;

    GruppoImballaggio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static GruppoImballaggio fromId(Integer id) {
        for (GruppoImballaggio at : GruppoImballaggio.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}