package it.ac.cargoflow.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "INCARICO_SEDE_MITT_DEST", indexes = {
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_INCARICO", columnList = "INCARICO_ID"),
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_SEDE_MITTENTE", columnList = "SEDE_MITTENTE_ID"),
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_SEDE_DESTINATARIO", columnList = "SEDE_DESTINATARIO_ID"),
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_AZIENDA", columnList = "AZIENDA_ID")
})
@Entity
public class IncaricoSedeMittDest {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "INCARICO_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Incarico incarico;

    @JoinColumn(name = "SEDE_MITTENTE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente sede_mittente;

    @JoinColumn(name = "SEDE_DESTINATARIO_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente sede_destinatario;

    @Column(name = "DAL", nullable = false)
    @NotNull
    private LocalDateTime dal;

    @Column(name = "AL")
    private LocalDateTime al;

    @Column(name = "AUTORIZZAZIONE")
    private String autorizzazione;

    @JoinColumn(name = "AZIENDA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Azienda azienda;

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public String getAutorizzazione() {
        return autorizzazione;
    }

    public void setAutorizzazione(String autorizzazione) {
        this.autorizzazione = autorizzazione;
    }

    public LocalDateTime getAl() {
        return al;
    }

    public void setAl(LocalDateTime al) {
        this.al = al;
    }

    public LocalDateTime getDal() {
        return dal;
    }

    public void setDal(LocalDateTime dal) {
        this.dal = dal;
    }

    public Cliente getSede_destinatario() {
        return sede_destinatario;
    }

    public void setSede_destinatario(Cliente sede_destinatario) {
        this.sede_destinatario = sede_destinatario;
    }

    public Cliente getSede_mittente() {
        return sede_mittente;
    }

    public void setSede_mittente(Cliente sede_mittente) {
        this.sede_mittente = sede_mittente;
    }

    public Incarico getIncarico() {
        return incarico;
    }

    public void setIncarico(Incarico incarico) {
        this.incarico = incarico;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}