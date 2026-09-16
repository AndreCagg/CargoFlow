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

    @Column(name = "LUN")
    private Boolean lun;

    @Column(name = "MAR")
    private Boolean mar;

    @Column(name = "MER")
    private Boolean mer;

    @Column(name = "GIO")
    private Boolean gio;

    @Column(name = "VEN")
    private Boolean ven;

    @Column(name = "SAB")
    private Boolean sab;

    @Column(name = "DOM")
    private Boolean dom;

    @Column(name = "DALLE")
    private LocalTime dalle;

    @Column(name = "ALLE")
    private LocalTime alle;

    @JoinColumn(name = "INCARICO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Incarico incarico;

    @Column(name = "RITIRO")
    private Boolean ritiro;

    @Column(name = "CONSEGNA")
    private Boolean consegna;

    public Boolean getConsegna() {
        return consegna;
    }

    public void setConsegna(Boolean consegna) {
        this.consegna = consegna;
    }

    public Boolean getRitiro() {
        return ritiro;
    }

    public void setRitiro(Boolean ritiro) {
        this.ritiro = ritiro;
    }

    public Boolean getDom() {
        return dom;
    }

    public void setDom(Boolean dom) {
        this.dom = dom;
    }

    public Boolean getSab() {
        return sab;
    }

    public void setSab(Boolean sab) {
        this.sab = sab;
    }

    public Boolean getVen() {
        return ven;
    }

    public void setVen(Boolean ven) {
        this.ven = ven;
    }

    public Boolean getGio() {
        return gio;
    }

    public void setGio(Boolean gio) {
        this.gio = gio;
    }

    public Boolean getMer() {
        return mer;
    }

    public void setMer(Boolean mer) {
        this.mer = mer;
    }

    public Boolean getMar() {
        return mar;
    }

    public void setMar(Boolean mar) {
        this.mar = mar;
    }

    public Boolean getLun() {
        return lun;
    }

    public void setLun(Boolean lun) {
        this.lun = lun;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}