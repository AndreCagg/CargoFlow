package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum OperatoriComp implements EnumClass<Integer> {

    GEQ(10),
    LEQ(20);

    private final Integer id;

    OperatoriComp(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static OperatoriComp fromId(Integer id) {
        for (OperatoriComp at : OperatoriComp.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}