package it.ac.cargoflow.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@JmixEntity
@Entity
public class StatoVariazioneSedi extends StatoAccessori {
    @JoinColumn(name = "SEDE_MITT_DEST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private IncaricoSedeMittDest sedeMittDest;

    @Column(name = "DATA_STATO", nullable = false)
    private LocalDateTime dataStato;

    public void setDataStato(LocalDateTime ts){
        this.dataStato=ts;
    }

    public LocalDateTime getDataStato(){
        return this.dataStato;
    }

    public IncaricoSedeMittDest getSedeMittDest() {
        return sedeMittDest;
    }

    public void setSedeMittDest(IncaricoSedeMittDest sedeMittDest) {
        this.sedeMittDest = sedeMittDest;
    }
}