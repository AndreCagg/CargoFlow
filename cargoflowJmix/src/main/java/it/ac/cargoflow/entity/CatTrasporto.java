package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum CatTrasporto implements EnumClass<Integer> {

    CAT1(50),
    CAT2(3),
    CAT3(1),
    CAT4(0),
    CAT0(99);

    private final Integer id;

    CatTrasporto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static CatTrasporto fromId(Integer id) {
        for (CatTrasporto at : CatTrasporto.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}