package it.ac.cargoflow.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@JmixEntity
@Table(name = "LQ")
@Entity
public class LQ {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @Column(name = "VALORE_COMBINATI_COLLO")
    private Double valoreCombinatiCollo;

    @Column(name = "COMBINATO_INTERNO_NON_AUTORIZZATO")
    private Boolean combinatoInternoNonAutorizzato;

    @Column(name = "COMBINATO_COLLO_NON_AUTORIZZATO")
    private Boolean combinatoColloNonAutorizzato;

    @Column(name = "PELLICOLA_INTERNO_NON_AUTORIZZATO")
    private Boolean pellicolaInternoNonAutorizzato;

    @Column(name = "PELLICOLA_COLLO_NON_AUTORIZZATO")
    private Boolean pellicolaColloNonAutorizzato;

    @Column(name = "COMBINATO_INTERNO_ILLIMITATO")
    private Boolean combinatoInternoIllimitato;

    @Column(name = "COMBINATO_COLLO_ILLIMITATO")
    private Boolean combinatoColloIllimitato;

    @Column(name = "PELLICOLA_INTERNO_ILLIMITATO")
    private Boolean pellicolaInternoIllimitato;

    @Column(name = "PELLICOLA_COLLO_ILLIMITATO")
    private Boolean pellicolaColloIllimitato;

    @Column(name = "COMBINATO_INTERNO_RISERVATO")
    private Boolean combinatoInternoRiservato;

    @Column(name = "COMBINATO_COLLO_RISERVATO")
    private Boolean combinatoColloRiservato;

    @Column(name = "PELLICOLA_INTERNO_RISERVATO")
    private Boolean pellicolaInternoRiservato;

    @Column(name = "LQ", nullable = false)
    @NotNull
    private Integer lq;

    @Column(name = "PELLICOLA_COLLO_RISERVATO")
    private Boolean pellicolaColloRiservato;

    @Column(name = "VALORE_PELLICOLA_INTERNO")
    private Double valorePellicolaInterno;

    @Column(name = "VALORE_PELLICOLA_COLLO")
    private Double valorePellicolaCollo;

    @Column(name = "VALORE")
    private Double valoreCombinatiInterno;

    @Column(name = "UM")
    private Integer um;

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

    public Integer getLq() {
        return lq;
    }

    public void setLq(Integer lq) {
        this.lq = lq;
    }

    public Boolean getPellicolaColloRiservato() {
        return pellicolaColloRiservato;
    }

    public void setPellicolaColloRiservato(Boolean pellicolaColloRiservato) {
        this.pellicolaColloRiservato = pellicolaColloRiservato;
    }

    public Boolean getPellicolaInternoRiservato() {
        return pellicolaInternoRiservato;
    }

    public void setPellicolaInternoRiservato(Boolean pellicolaInternoRiservato) {
        this.pellicolaInternoRiservato = pellicolaInternoRiservato;
    }

    public Boolean getCombinatoColloRiservato() {
        return combinatoColloRiservato;
    }

    public void setCombinatoColloRiservato(Boolean combinatoColloRiservato) {
        this.combinatoColloRiservato = combinatoColloRiservato;
    }

    public Boolean getCombinatoInternoRiservato() {
        return combinatoInternoRiservato;
    }

    public void setCombinatoInternoRiservato(Boolean combinatoInternoRiservato) {
        this.combinatoInternoRiservato = combinatoInternoRiservato;
    }

    public Boolean getPellicolaColloIllimitato() {
        return pellicolaColloIllimitato;
    }

    public void setPellicolaColloIllimitato(Boolean pellicolaColloIllimitato) {
        this.pellicolaColloIllimitato = pellicolaColloIllimitato;
    }

    public Boolean getPellicolaInternoIllimitato() {
        return pellicolaInternoIllimitato;
    }

    public void setPellicolaInternoIllimitato(Boolean pellicolaInternoIllimitato) {
        this.pellicolaInternoIllimitato = pellicolaInternoIllimitato;
    }

    public Boolean getCombinatoColloIllimitato() {
        return combinatoColloIllimitato;
    }

    public void setCombinatoColloIllimitato(Boolean combinatoColloIllimitato) {
        this.combinatoColloIllimitato = combinatoColloIllimitato;
    }

    public Boolean getCombinatoInternoIllimitato() {
        return combinatoInternoIllimitato;
    }

    public void setCombinatoInternoIllimitato(Boolean combinatoInternoIllimitato) {
        this.combinatoInternoIllimitato = combinatoInternoIllimitato;
    }

    public Boolean getPellicolaColloNonAutorizzato() {
        return pellicolaColloNonAutorizzato;
    }

    public void setPellicolaColloNonAutorizzato(Boolean pellicolaColloNonAutorizzato) {
        this.pellicolaColloNonAutorizzato = pellicolaColloNonAutorizzato;
    }

    public Boolean getPellicolaInternoNonAutorizzato() {
        return pellicolaInternoNonAutorizzato;
    }

    public void setPellicolaInternoNonAutorizzato(Boolean pellicolaInternoNonAutorizzato) {
        this.pellicolaInternoNonAutorizzato = pellicolaInternoNonAutorizzato;
    }

    public Boolean getCombinatoColloNonAutorizzato() {
        return combinatoColloNonAutorizzato;
    }

    public void setCombinatoColloNonAutorizzato(Boolean combinatoColloNonAutorizzato) {
        this.combinatoColloNonAutorizzato = combinatoColloNonAutorizzato;
    }

    public Boolean getCombinatoInternoNonAutorizzato() {
        return combinatoInternoNonAutorizzato;
    }

    public void setCombinatoInternoNonAutorizzato(Boolean combinatoInternoNonAutorizzato) {
        this.combinatoInternoNonAutorizzato = combinatoInternoNonAutorizzato;
    }

    public Double getValorePellicolaCollo() {
        return valorePellicolaCollo;
    }

    public void setValorePellicolaCollo(Double valorePellicolaCollo) {
        this.valorePellicolaCollo = valorePellicolaCollo;
    }

    public Double getValorePellicolaInterno() {
        return valorePellicolaInterno;
    }

    public void setValorePellicolaInterno(Double valorePellicolaInterno) {
        this.valorePellicolaInterno = valorePellicolaInterno;
    }

    public Double getValoreCombinatiCollo() {
        return valoreCombinatiCollo;
    }

    public void setValoreCombinatiCollo(Double valoreCombinatiCollo) {
        this.valoreCombinatiCollo = valoreCombinatiCollo;
    }

    public UM getUm() {
        return um == null ? null : UM.fromId(um);
    }

    public void setUm(UM um) {
        this.um = um == null ? null : um.getId();
    }

    public Double getValoreCombinatiInterno() {
        return valoreCombinatiInterno;
    }

    public void setValoreCombinatiInterno(Double valoreCombinatiInterno) {
        this.valoreCombinatiInterno = valoreCombinatiInterno;
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

}