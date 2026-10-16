package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;

@JmixEntity
@Table(name = "CODICE_CISTERNA")
@Entity
public class DisposizioniCisternaADR extends RequisitoVeicolo {
//    @JmixGeneratedValue
//    @Column(name = "ID", nullable = false)
//    @Id
//    private Integer id;
//
//    @NotNull
//    @Column(name = "DESCRIZIONE", nullable = false)
//    @Lob
//    private String descrizione;

    //@NotNull
    //@Column(name = "TIPO", nullable = false)
    @Column(name = "TIPO")
    private Integer tipo;

    @OnDelete(DeletePolicy.DENY)
    @JoinTable(name = "VEICOLI_TRASPORTO_DISPOSIZIONI_CISTERNA_ADR_LINK",
            joinColumns = @JoinColumn(name = "DISPOSIZIONI_CISTERNA_A_D_R_ID"),
            inverseJoinColumns = @JoinColumn(name = "VEICOLI_TRASPORTO_ID"))
    @ManyToMany
    private List<VeicoliTrasporto> veicoliTrasportoes1;

    public List<VeicoliTrasporto> getVeicoliTrasportoes1() {
        return veicoliTrasportoes1;
    }

    public void setVeicoliTrasportoes1(List<VeicoliTrasporto> veicoliTrasportoes1) {
        this.veicoliTrasportoes1 = veicoliTrasportoes1;
    }

//    @NotNull
//    @Column(name = "NUM", nullable = false)
//    private String num;
//
//    @Column(name = "VERSION", nullable = false)
//    @Version
//    private Integer version;
//
//    @CreatedBy
//    @Column(name = "CREATED_BY")
//    private String createdBy;
//
//    @CreatedDate
//    @Column(name = "CREATED_DATE")
//    private OffsetDateTime createdDate;
//
//    @LastModifiedBy
//    @Column(name = "LAST_MODIFIED_BY")
//    private String lastModifiedBy;
//
//    @LastModifiedDate
//    @Column(name = "LAST_MODIFIED_DATE")
//    private OffsetDateTime lastModifiedDate;
//
//    @DeletedBy
//    @Column(name = "DELETED_BY")
//    private String deletedBy;
//
//    @DeletedDate
//    @Column(name = "DELETED_DATE")
//    private OffsetDateTime deletedDate;
//
//    public String getDescrizione() {
//        return descrizione;
//    }
//
//    public void setDescrizione(String descrizione) {
//        this.descrizione = descrizione;
//    }

    public void setTipo(DisposizioniCisterna tipo) {
        this.tipo = tipo == null ? null : tipo.getId();
    }

    public DisposizioniCisterna getTipo() {
        return tipo == null ? null : DisposizioniCisterna.fromId(tipo);
    }

//    public String getNum() {
//        return num;
//    }
//
//    public void setNum(String num) {
//        this.num = num;
//    }
//
//    public OffsetDateTime getDeletedDate() {
//        return deletedDate;
//    }
//
//    public void setDeletedDate(OffsetDateTime deletedDate) {
//        this.deletedDate = deletedDate;
//    }
//
//    public String getDeletedBy() {
//        return deletedBy;
//    }
//
//    public void setDeletedBy(String deletedBy) {
//        this.deletedBy = deletedBy;
//    }
//
//    public OffsetDateTime getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
//
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    public OffsetDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(OffsetDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    @InstanceName
    @DependsOnProperties({"num", "tipo"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s%s",
                metadataTools.format(getTipo()),
                metadataTools.format(super.getNum()));
    }
}