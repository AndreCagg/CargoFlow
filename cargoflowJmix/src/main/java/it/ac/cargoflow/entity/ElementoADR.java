package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
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
@Table(name = "ELEMENTO_ADR", indexes = {
        @Index(name = "IDX_ELEMENTO_ADR_CLASSE_PERICOLO", columnList = "CLASSE_PERICOLO_ID"),
        @Index(name = "IDX_ELEMENTO_ADR_IMBALLAGGIO_COMUNE", columnList = "IMBALLAGGIO_COMUNE_ID"),
        @Index(name = "IDX_ELEMENTO_ADR_CODICE_CISTERNA", columnList = "CODICE_CISTERNA")
})
@Entity
public class ElementoADR {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "ONU", nullable = false, length = 4)
    private String onu;

    @NotNull
    @Column(name = "DENOMINAZIONE", nullable = false, length = 100)
    private String denominazione;

    @Column(name = "GRUPPO_IMBALLAGGIO")
    private Integer gruppoImballaggio;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "CLASSE_PERICOLO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ClassePericolo classePericolo;

    @JoinTable(name = "ELEMENTO_ADR_ETICHETTE_ADR_LINK",
            joinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ETICHETTE_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    @OnDeleteInverse(DeletePolicy.DENY)
    private List<EtichetteADR> etichette;

    @JoinTable(name = "ELEMENTO_ADR_DISPOSIZIONI_SPECIALI_ADR_LINK",
            joinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DISPOSIZIONI_SPECIALI_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    @OnDeleteInverse(DeletePolicy.DENY)
    private List<DisposizioniSpecialiADR> disposizioniSpeciali;

    @NotNull
    @Column(name = "QUANTITA_LIMITATE", nullable = false)
    private Integer quantitaLimitate;

    @NotNull
    @Column(name = "QUANTITA_ESENTI", nullable = false)
    private Integer quantitaEsenti;

    @JoinTable(name = "ELEMENTO_ADR_ISTRUZIONI_IMBALLAGGIO_ADR_LINK",
            joinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ISTRUZIONI_IMBALLAGGIO_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    @OnDeleteInverse(DeletePolicy.DENY)
    private List<IstruzioniImballaggioADR> istruzioniImballaggio;

    @JoinTable(name = "ELEMENTO_ADR_DISPOSIZIONI_SPECIALI_IMBALL_ADR_LINK",
            joinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DISPOSIZIONI_SPECIALI_IMBALL_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    @OnDeleteInverse(DeletePolicy.DENY)
    private List<DisposizioniSpecialiImballADR> disposizioniImballaggio;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "IMBALLAGGIO_COMUNE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ImballaggioComune imballaggioComune;

    @Column(name = "CODICE_CISTERNA")
    private Integer codiceCisterna;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "DISPOSIZIONI_CISTERNA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private DisposizioniCisternaADR disposizioniCisterna;

    @NotNull
    @Column(name = "CATEGORIA_TRASPORTO", nullable = false)
    private Integer categoriaTrasporto;

    @Column(name = "LIMITE_GALLERIA")
    private Integer limiteGalleria;

    @Column(name = "COD_RESTRIZIONE_A")
    private Character codRestrizioneA;

    @Column(name = "COD_RESTRIZIONE_B")
    private Character codRestrizioneB;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "TRASPORTO_COLLI_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrasportoColli trasportoColli;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "RINFUSA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrasportoRinfusa rinfusa;

    @JoinTable(name = "ELEMENTO_ADR_CARICO_SCARICO_MOVIMENTAZIONE_LINK",
            joinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CARICO_SCARICO_MOVIMENTAZIONE_ID", referencedColumnName = "ID"))
    @ManyToMany
    @OnDeleteInverse(DeletePolicy.DENY)
    private List<CaricoScaricoMovimentazione> caricoScaricoMovimentazione;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinTable(name = "ELEMENTO_ADR_ESERCIZI_LINK",
            joinColumns = @JoinColumn(name = "ELEMENTO_A_D_R_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ESERCIZI_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Esercizi> esercizi;

    @Column(name = "CODICE_PERICOLO", length = 5)
    private String codicePericolo;

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

    public void setEsercizi(List<Esercizi> esercizi) {
        this.esercizi = esercizi;
    }

    public List<Esercizi> getEsercizi() {
        return esercizi;
    }

    public void setDisposizioniCisterna(DisposizioniCisternaADR disposizioniCisterna) {
        this.disposizioniCisterna = disposizioniCisterna;
    }

    public DisposizioniCisternaADR getDisposizioniCisterna() {
        return disposizioniCisterna;
    }

    public void setCodiceCisterna(TipoCisterna codiceCisterna) {
        this.codiceCisterna = codiceCisterna == null ? null : codiceCisterna.getId();
    }

    public TipoCisterna getCodiceCisterna() {
        return codiceCisterna == null ? null : TipoCisterna.fromId(codiceCisterna);
    }

    public String getCodicePericolo() {
        return codicePericolo;
    }

    public void setCodicePericolo(String codicePericolo) {
        this.codicePericolo = codicePericolo;
    }

    public List<CaricoScaricoMovimentazione> getCaricoScaricoMovimentazione() {
        return caricoScaricoMovimentazione;
    }

    public void setCaricoScaricoMovimentazione(List<CaricoScaricoMovimentazione> caricoScaricoMovimentazione) {
        this.caricoScaricoMovimentazione = caricoScaricoMovimentazione;
    }

    public void setRinfusa(TrasportoRinfusa rinfusa) {
        this.rinfusa = rinfusa;
    }

    public TrasportoRinfusa getRinfusa() {
        return rinfusa;
    }

    public void setTrasportoColli(TrasportoColli trasportoColli) {
        this.trasportoColli = trasportoColli;
    }

    public TrasportoColli getTrasportoColli() {
        return trasportoColli;
    }

    public Character getCodRestrizioneB() {
        return codRestrizioneB;
    }

    public void setCodRestrizioneB(Character codRestrizioneB) {
        this.codRestrizioneB = codRestrizioneB;
    }

    public Character getCodRestrizioneA() {
        return codRestrizioneA;
    }

    public void setCodRestrizioneA(Character codRestrizioneA) {
        this.codRestrizioneA = codRestrizioneA;
    }

    public Integer getLimiteGalleria() {
        return limiteGalleria;
    }

    public void setLimiteGalleria(Integer limiteGalleria) {
        this.limiteGalleria = limiteGalleria;
    }

    public Integer getCategoriaTrasporto() {
        return categoriaTrasporto;
    }

    public void setCategoriaTrasporto(Integer categoriaTrasporto) {
        this.categoriaTrasporto = categoriaTrasporto;
    }

    public ImballaggioComune getImballaggioComune() {
        return imballaggioComune;
    }

    public void setImballaggioComune(ImballaggioComune imballaggioComune) {
        this.imballaggioComune = imballaggioComune;
    }

    public List<DisposizioniSpecialiImballADR> getDisposizioniImballaggio() {
        return disposizioniImballaggio;
    }

    public void setDisposizioniImballaggio(List<DisposizioniSpecialiImballADR> disposizioniImballaggio) {
        this.disposizioniImballaggio = disposizioniImballaggio;
    }

    public List<IstruzioniImballaggioADR> getIstruzioniImballaggio() {
        return istruzioniImballaggio;
    }

    public void setIstruzioniImballaggio(List<IstruzioniImballaggioADR> istruzioniImballaggio) {
        this.istruzioniImballaggio = istruzioniImballaggio;
    }

    public Integer getQuantitaEsenti() {
        return quantitaEsenti;
    }

    public void setQuantitaEsenti(Integer quantitaEsenti) {
        this.quantitaEsenti = quantitaEsenti;
    }

    public Integer getQuantitaLimitate() {
        return quantitaLimitate;
    }

    public void setQuantitaLimitate(Integer quantitaLimitate) {
        this.quantitaLimitate = quantitaLimitate;
    }

    public List<DisposizioniSpecialiADR> getDisposizioniSpeciali() {
        return disposizioniSpeciali;
    }

    public void setDisposizioniSpeciali(List<DisposizioniSpecialiADR> disposizioniSpeciali) {
        this.disposizioniSpeciali = disposizioniSpeciali;
    }

    public List<EtichetteADR> getEtichette() {
        return etichette;
    }

    public void setEtichette(List<EtichetteADR> etichette) {
        this.etichette = etichette;
    }

    public ClassePericolo getClassePericolo() {
        return classePericolo;
    }

    public void setClassePericolo(ClassePericolo classePericolo) {
        this.classePericolo = classePericolo;
    }

    public GruppoImballaggio getGruppoImballaggio() {
        return gruppoImballaggio == null ? null : GruppoImballaggio.fromId(gruppoImballaggio);
    }

    public void setGruppoImballaggio(GruppoImballaggio gruppoImballaggio) {
        this.gruppoImballaggio = gruppoImballaggio == null ? null : gruppoImballaggio.getId();
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getOnu() {
        return onu;
    }

    public void setOnu(String onu) {
        this.onu = onu;
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

    @InstanceName
    @DependsOnProperties({"onu"})
    public String getInstanceName(MetadataTools metadataTools) {
        return "UN"+metadataTools.format(onu);
    }
}