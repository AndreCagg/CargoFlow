package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.datatoolsflowui.view.entityinspector.ShowMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "MERCE", indexes = {
        @Index(name = "IDX_MERCE_1_INCARICO", columnList = "INCARICO_ID")
})
@Entity
public class Merce {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "SEGNACOLLO", nullable = false)
    @NotNull
    private String segnacollo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(DeletePolicy.CASCADE)
    @JoinColumn(name = "INCARICO_ID", nullable = false)
    private Incarico incarico;

    @Column(name = "MERCE_TIPO")
    private String merce_tipo;

    @Column(name = "PESO_KG")
    private Double peso_kg;

    @Column(name = "VOLUME_M3")
    private Double volume_m3;

    @Column(name = "FRAGILE", nullable = false)
    @NotNull
    private Boolean fragile = false;

    @Column(name = "EPAL", nullable = false)
    @NotNull
    private Boolean epal = false;

    @Column(name = "ID_EPAL", length = 50)
    private String id_epal;

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

    public String getId_epal() {
        return id_epal;
    }

    public void setId_epal(String id_epal) {
        this.id_epal = id_epal;
    }

    public Boolean getEpal() {
        return epal;
    }

    public void setEpal(Boolean epal) {
        this.epal = epal;
    }

    public Boolean getFragile() {
        return fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public Double getVolume_m3() {
        return volume_m3;
    }

    public void setVolume_m3(Double volume_m3) {
        this.volume_m3 = volume_m3;
    }

    public Double getPeso_kg() {
        return peso_kg;
    }

    public void setPeso_kg(Double peso_kg) {
        this.peso_kg = peso_kg;
    }

    public ShowMode getMerce_tipo() {
        return merce_tipo == null ? null : ShowMode.fromId(merce_tipo);
    }

    public void setMerce_tipo(ShowMode merce_tipo) {
        this.merce_tipo = merce_tipo == null ? null : merce_tipo.getId();
    }

    public Incarico getIncarico() {
        return incarico;
    }

    public void setIncarico(Incarico incarico) {
        this.incarico = incarico;
    }

    public String getSegnacollo() {
        return segnacollo;
    }

    public void setSegnacollo(String segnacollo) {
        this.segnacollo = segnacollo;
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