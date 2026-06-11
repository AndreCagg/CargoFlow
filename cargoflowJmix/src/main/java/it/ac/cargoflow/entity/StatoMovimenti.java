package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum StatoMovimenti implements EnumClass<Integer> {

    INSERITO(10);

    private final Integer id;

    StatoMovimenti(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static StatoMovimenti fromId(Integer id) {
        for (StatoMovimenti at : StatoMovimenti.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}