package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Ruoli implements EnumClass<String> {

    ADMIN_SAAS("A"),
    ADMIN_AZIENDA("B"),
    OPERATORE_SEDE("C");

    private final String id;

    Ruoli(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Ruoli fromId(String id) {
        for (Ruoli at : Ruoli.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}