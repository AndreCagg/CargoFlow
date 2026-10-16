package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum Mta implements EnumClass<Integer> {

    A3_5(10),
    B7_5(20),
    MAGGIORE7_5(30);

    private final Integer id;

    Mta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Mta fromId(Integer id) {
        for (Mta at : Mta.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}