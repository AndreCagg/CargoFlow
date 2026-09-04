package it.ac.cargoflow.entity;

import io.jmix.core.MetadataTools;
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

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "FASCIA_ORARIA", indexes = {
        @Index(name = "IDX_FASCIA_ORARIA_CLIENTE", columnList = "CLIENTE_ID")
})
@Entity
public class FasciaOraria {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente cliente;

    @Column(name = "DALLE")
    private LocalTime dalle;

    @Column(name = "ALLE")
    private LocalTime alle;

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

    @Column(name = "SOLO_RITIRO")
    private Boolean solo_ritiro;

    @Column(name = "SOLO_CONSEGNA")
    private Boolean solo_consegna;

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

    public Boolean getSolo_consegna() {
        return solo_consegna;
    }

    public void setSolo_consegna(Boolean solo_consegna) {
        this.solo_consegna = solo_consegna;
    }

    public Boolean getSolo_ritiro() {
        return solo_ritiro;
    }

    public void setSolo_ritiro(Boolean solo_ritiro) {
        this.solo_ritiro = solo_ritiro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    /*@InstanceName
    @DependsOnProperties({"giorno", "dalle", "alle"})
    public String getInstanceName(MetadataTools metadataTools, DatatypeFormatter datatypeFormatter) {
        return String.format("%s %s %s",
                metadataTools.format(giorno),
                datatypeFormatter.formatLocalTime(dalle),
                datatypeFormatter.formatLocalTime(alle));
    }*/
    @InstanceName
    @DependsOnProperties({"dalle", "alle"})
    public String getInstanceName(MetadataTools metadataTools, DatatypeFormatter datatypeFormatter) {
        return String.format("%s %s %s",
                datatypeFormatter.formatLocalTime(dalle),
                datatypeFormatter.formatLocalTime(alle));
    }
}