package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@JmixEntity
@Entity
public class StatoVariazioneSedi extends StatoAccessori {
    @JoinColumn(name = "SEDE_MITT_DEST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private IncaricoSedeMittDest sedeMittDest;

    public IncaricoSedeMittDest getSedeMittDest() {
        return sedeMittDest;
    }

    public void setSedeMittDest(IncaricoSedeMittDest sedeMittDest) {
        this.sedeMittDest = sedeMittDest;
    }
}