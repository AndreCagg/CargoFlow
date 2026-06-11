package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum StatoIntegrazione implements EnumClass<Integer> {

    VALIDO(10),
    RICHIESTA_INTEGRAZIONE_MITT_INVIATA(20),
    INTEGRAZIONE_MITT_RICEVUTA(30),
    RICHIESTA_INTEGRAZIONE_MITT_RIFUITATA(40),
    RICHIESTA_INTEGRAZIONE_DEST_RIFUITATA(50),
    RICHIESTA_INTEGRAZIONE_DEST_INVIATA(60),
    INTEGRAZIONE_DEST_RICEVUTA(70);

    private final Integer id;

    StatoIntegrazione(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static StatoIntegrazione fromId(Integer id) {
        for (StatoIntegrazione at : StatoIntegrazione.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}