package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "CARICO_SCARICO_MOVIMENTAZIONE", indexes = {
        @Index(name = "IDX_CARICO_SCARICO_MOVIMENTAZIONE_ELEMENTO_ADR", columnList = "ELEMENTO_ADR_ID")
})
@Entity
public class CaricoScaricoMovimentazione {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "SIGLA", nullable = false, length = 4)
    private String sigla;

    @NotNull
    @Column(name = "EMERGENZA", nullable = false)
    private String emergenza;

    @NotNull
    @Column(name = "AZIONE", nullable = false)
    @Lob
    private String azione;

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
    @JoinTable(name = "ELEMENTO_ADR_CARICO_SCARICO_MOVIMENTAZIONE_LINK",
            joinColumns = @JoinColumn(name = "CARICO_SCARICO_MOVIMENTAZIONE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ElementoADR> elementoADRs;

    public List<ElementoADR> getElementoADRs() {
        return elementoADRs;
    }

    public void setElementoADRs(List<ElementoADR> elementoADRs) {
        this.elementoADRs = elementoADRs;
    }

    public String getAzione() {
        return azione;
    }

    public void setAzione(String azione) {
        this.azione = azione;
    }

    public String getEmergenza() {
        return emergenza;
    }

    public void setEmergenza(String emergenza) {
        this.emergenza = emergenza;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public ElementoADR getElementoADR() {
        return elementoADR;
    }

    public void setElementoADR(ElementoADR elementoADR) {
        this.elementoADR = elementoADR;
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