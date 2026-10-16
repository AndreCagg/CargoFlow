package it.ac.cargoflow.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@JmixEntity
@Table(name = "EQ")
@Entity
public class Eq {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @Column(name = "CODICE", nullable = false)
    @NotNull
    private Integer codice;

    @Column(name = "QTA_INTERNO")
    private Double qtaInterno;

    @Column(name = "VIETATO")
    private Boolean vietatoInterno;

    @Column(name = "VIETATO_ESTERNO")
    private Boolean vietatoEsterno;

    @Column(name = "QTA_ESTERNO")
    private Double qtaEsterno;

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

    public Boolean getVietatoEsterno() {
        return vietatoEsterno;
    }

    public void setVietatoEsterno(Boolean vietatoEsterno) {
        this.vietatoEsterno = vietatoEsterno;
    }

    public Double getQtaEsterno() {
        return qtaEsterno;
    }

    public void setQtaEsterno(Double qtaEsterno) {
        this.qtaEsterno = qtaEsterno;
    }

    public Boolean getVietatoInterno() {
        return vietatoInterno;
    }

    public void setVietatoInterno(Boolean vietatoInterno) {
        this.vietatoInterno = vietatoInterno;
    }

    public Double getQtaInterno() {
        return qtaInterno;
    }

    public void setQtaInterno(Double qtaInterno) {
        this.qtaInterno = qtaInterno;
    }

    public Integer getCodice() {
        return codice;
    }

    public void setCodice(Integer codice) {
        this.codice = codice;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"codice"})
    public String getInstanceName(DatatypeFormatter datatypeFormatter) {
        return "E"+datatypeFormatter.formatInteger(codice);
    }
}