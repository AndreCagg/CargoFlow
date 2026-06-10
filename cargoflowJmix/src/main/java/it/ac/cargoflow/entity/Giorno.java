package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Giorno implements EnumClass<Integer> {

    LUNEDÌ(0),
    MARTEDÌ(1),
    MERCOLEDÌ(2),
    GIOVEDÌ(3),
    VENERDÌ(4),
    SABATO(5),
    DOMENICA(6);

    private final Integer id;

    Giorno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Giorno fromId(Integer id) {
        for (Giorno at : Giorno.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}