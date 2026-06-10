package it.ac.cargoflow.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@JmixEntity
@Table(name = "INCARICO_FASCIA_ORARIA", indexes = {
        @Index(name = "IDX_INCARICO_FASCIA_ORARIA_INCARICO", columnList = "INCARICO_ID")
})
@Entity
public class IncaricoFasciaOraria {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "GIORNO")
    private Integer giorno;

    @Column(name = "DALLE")
    private LocalTime dalle;

    @Column(name = "ALLE")
    private LocalTime alle;

    @JoinColumn(name = "INCARICO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Incarico incarico;

    @Column(name = "SOLO_RITIRO")
    private Boolean soloRitiro;

    @Column(name = "SOLO_CONSEGNA")
    private Boolean soloConsegna;

    public Boolean getSoloConsegna() {
        return soloConsegna;
    }

    public void setSoloConsegna(Boolean soloConsegna) {
        this.soloConsegna = soloConsegna;
    }

    public Boolean getSoloRitiro() {
        return soloRitiro;
    }

    public void setSoloRitiro(Boolean soloRitiro) {
        this.soloRitiro = soloRitiro;
    }

    public Incarico getIncarico() {
        return incarico;
    }

    public void setIncarico(Incarico incarico) {
        this.incarico = incarico;
    }

    public LocalTime getAlle() {
        return alle;
    }

    public void setAlle(LocalTime alle) {
        this.alle = alle;
    }

    public LocalTime getDalle() {
        return dalle;
    }

    public void setDalle(LocalTime dalle) {
        this.dalle = dalle;
    }

    public Giorno getGiorno() {
        return giorno == null ? null : Giorno.fromId(giorno);
    }

    public void setGiorno(Giorno giorno) {
        this.giorno = giorno == null ? null : giorno.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}