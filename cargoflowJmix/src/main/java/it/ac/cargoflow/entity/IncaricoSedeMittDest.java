package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "INCARICO_SEDE_MITT_DEST", indexes = {
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_INCARICO", columnList = "INCARICO_ID"),
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_SEDE_MITTENTE", columnList = "SEDE_MITTENTE_ID"),
        @Index(name = "IDX_INCARICO_SEDE_MITT_DEST_SEDE_DESTINATARIO", columnList = "SEDE_DESTINATARIO_ID")
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

    @Column(name = "DAL")
    private LocalDateTime dal;

    @Column(name = "AL")
    private LocalDateTime al;

    @Column(name = "AUTORIZZAZIONE")
    private String autorizzazione;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "sedeMittDest")
    private List<StatoVariazioneSedi> stato;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private OffsetDateTime lastModifiedDate;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    private OffsetDateTime deletedDate;

    @JmixProperty
    @DependsOnProperties("stato")
    public StatoVariazioneSedi getUltimoStato() {
        if (this.stato == null || this.stato.isEmpty()) {
            return null;
        }

        return this.stato.stream()
                .filter(s -> s.getCreatedDate() != null)
                .max(Comparator.comparing(StatoVariazioneSedi::getCreatedDate))
                .orElse(null);
    }

    public List<StatoVariazioneSedi> getStato() {
        return stato;
    }

    public void setStato(List<StatoVariazioneSedi> stato) {
        this.stato = stato;
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

    public OffsetDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(OffsetDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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