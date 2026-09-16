package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "DISPOSIZIONI_SPECIALI_IMBALL_ADR", indexes = {
        @Index(name = "IDX_DISPOSIZIONI_SPECIALI_IMBALL_ADR_ELEMENTO_ADR", columnList = "ELEMENTO_ADR_ID")
})
@Entity
public class DisposizioniSpecialiImballADR {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "TIPO")
    private Integer tipo;

    @Column(name = "NUM", length = 3)
    private String num;

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

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "ELEMENTO_ADR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ElementoADR elementoADR;

    @OnDelete(DeletePolicy.DENY)
    @JoinTable(name = "ELEMENTO_ADR_DISPOSIZIONI_SPECIALI_IMBALL_ADR_LINK",
            joinColumns = @JoinColumn(name = "DISPOSIZIONI_SPECIALI_IMBALL_A_D_R_ID"),
            inverseJoinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID"))
    @ManyToMany
    private List<ElementoADR> elementoADRs;

    public List<ElementoADR> getElementoADRs() {
        return elementoADRs;
    }

    public void setElementoADRs(List<ElementoADR> elementoADRs) {
        this.elementoADRs = elementoADRs;
    }

    public ElementoADR getElementoADR() {
        return elementoADR;
    }

    public void setElementoADR(ElementoADR elementoADR) {
        this.elementoADR = elementoADR;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public TipoDispImball getTipo() {
        return tipo == null ? null : TipoDispImball.fromId(tipo);
    }

    public void setTipo(TipoDispImball tipo) {
        this.tipo = tipo == null ? null : tipo.getId();
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}