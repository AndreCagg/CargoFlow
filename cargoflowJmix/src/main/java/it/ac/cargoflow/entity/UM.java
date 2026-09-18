package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum UM implements EnumClass<Integer> {

    L(10),
    KG(20),
    ML(30);

    private final Integer id;

    UM(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static UM fromId(Integer id) {
        for (UM at : UM.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}