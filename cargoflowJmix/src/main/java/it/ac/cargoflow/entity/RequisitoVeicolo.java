package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
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
import java.util.List;

@JmixEntity
@Table(name = "REQUISITO_VEICOLO")
@Entity
public class RequisitoVeicolo {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @Column(name = "TIPO_REQUISITO")
    private Integer tipoRequisito;

    @NotNull
    @Column(name = "DESCRIZIONE", nullable = false)
    @Lob
    private String descrizione;

    @NotNull
    @Column(name = "NUM", nullable = false)
    private Integer num;

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

    @OnDelete(DeletePolicy.DENY)
    @JoinTable(name = "VEICOLI_TRASPORTO_REQUISITO_VEICOLO_LINK",
            joinColumns = @JoinColumn(name = "REQUISITO_VEICOLO_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "VEICOLI_TRASPORTO_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<VeicoliTrasporto> veicoliTrasportoes;

    public TipoRequisitiVeicolo getTipoRequisito() {
        return tipoRequisito == null ? null : TipoRequisitiVeicolo.fromId(tipoRequisito);
    }

    public void setTipoRequisito(TipoRequisitiVeicolo tipoRequisito) {
        this.tipoRequisito = tipoRequisito == null ? null : tipoRequisito.getId();
    }

    public List<VeicoliTrasporto> getVeicoliTrasportoes() {
        return veicoliTrasportoes;
    }

    public void setVeicoliTrasportoes(List<VeicoliTrasporto> veicoliTrasportoes) {
        this.veicoliTrasportoes = veicoliTrasportoes;
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

    public Long getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"id"})
    public String getInstanceName(DatatypeFormatter datatypeFormatter) {
        return datatypeFormatter.formatLong(id);
    }
}