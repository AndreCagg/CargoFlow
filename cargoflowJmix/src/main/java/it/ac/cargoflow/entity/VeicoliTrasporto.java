package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JmixEntity
@Entity
public class VeicoliTrasporto extends Veicoli {
    @NotNull
    @Column(name = "TARGA")
    private String targa;

    @Column(name = "ADR")
    private Boolean adr;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "AZIENDA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sede azienda;

    @Column(name = "TIPOLOGIA")
    private Integer tipologia;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinTable(name = "VEICOLI_TRASPORTO_CERTIFICATI_VEICOLO_ADR_LINK",
            joinColumns = @JoinColumn(name = "VEICOLI_TRASPORTO_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CERTIFICATI_VEICOLO_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<CertificatiVeicoloADR> certificati;

    @Column(name = "PANNELLI_ADR")
    private Boolean pannelliAdr;

    @Column(name = "ETICHETTE_ADR")
    private Boolean etichetteAdr;

    @Column(name = "BORSA_EQ_ADR")
    private Boolean borsaEqAdr;

    @Column(name = "KG_ESTINTORE")
    private Double kgEstintore;

    @Column(name = "MTA")
    private Integer mta;

    @Column(name = "PORTATA_UTILE")
    private Double portataUtile;

    @Column(name = "VOLUME_UTILE")
    private Double volumeUtile;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "CODICE_CISTERNA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private VeicoloTrasportoADRGerarchia codiceCisterna;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinTable(name = "VEICOLI_TRASPORTO_DISPOSIZIONI_CISTERNA_ADR_LINK",
            joinColumns = @JoinColumn(name = "VEICOLI_TRASPORTO_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DISPOSIZIONI_CISTERNA_A_D_R_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<DisposizioniCisternaADR> disposizioniCisterna;

    @Column(name = "PRESSIONE_CALCOLO")
    private Double pressioneCalcolo;

    @Column(name = "PRESSIONE_PROVA")
    private Double pressioneProva;

    public Boolean getBorsaEqAdr() {
        return borsaEqAdr;
    }

    public void setBorsaEqAdr(Boolean borsaEqAdr) {
        this.borsaEqAdr = borsaEqAdr;
    }

    public Boolean getEtichetteAdr() {
        return etichetteAdr;
    }

    public void setEtichetteAdr(Boolean etichetteAdr) {
        this.etichetteAdr = etichetteAdr;
    }

    public Boolean getPannelliAdr() {
        return pannelliAdr;
    }

    public void setPannelliAdr(Boolean pannelliAdr) {
        this.pannelliAdr = pannelliAdr;
    }

    public Double getKgEstintore() {
        return kgEstintore;
    }

    public void setKgEstintore(Double kgEstintore) {
        this.kgEstintore = kgEstintore;
    }

    public void setMta(Mta mta) {
        this.mta = mta == null ? null : mta.getId();
    }

    public Mta getMta() {
        return mta == null ? null : Mta.fromId(mta);
    }

    public Double getPressioneProva() {
        return pressioneProva;
    }

    public void setPressioneProva(Double pressioneProva) {
        this.pressioneProva = pressioneProva;
    }

    public Double getPressioneCalcolo() {
        return pressioneCalcolo;
    }

    public void setPressioneCalcolo(Double pressioneCalcolo) {
        this.pressioneCalcolo = pressioneCalcolo;
    }

    public List<DisposizioniCisternaADR> getDisposizioniCisterna() {
        return disposizioniCisterna;
    }

    public void setDisposizioniCisterna(List<DisposizioniCisternaADR> disposizioniCisterna) {
        this.disposizioniCisterna = disposizioniCisterna;
    }

    public VeicoloTrasportoADRGerarchia getCodiceCisterna() {
        return codiceCisterna;
    }

    public void setCodiceCisterna(VeicoloTrasportoADRGerarchia codiceCisterna) {
        this.codiceCisterna = codiceCisterna;
    }

    public Double getVolumeUtile() {
        return volumeUtile;
    }

    public void setVolumeUtile(Double volumeUtile) {
        this.volumeUtile = volumeUtile;
    }

    public Double getPortataUtile() {
        return portataUtile;
    }

    public void setPortataUtile(Double portataUtile) {
        this.portataUtile = portataUtile;
    }

    public List<CertificatiVeicoloADR> getCertificati() {
        return certificati;
    }

    public void setCertificati(List<CertificatiVeicoloADR> certificati) {
        this.certificati = certificati;
    }

    public TipoVeicolo getTipologia() {
        return tipologia == null ? null : TipoVeicolo.fromId(tipologia);
    }

    public void setTipologia(TipoVeicolo tipologia) {
        this.tipologia = tipologia == null ? null : tipologia.getId();
    }

    public Sede getAzienda() {
        return azienda;
    }

    public void setAzienda(Sede azienda) {
        this.azienda = azienda;
    }

    public Boolean getAdr() {
        return adr;
    }

    public void setAdr(Boolean adr) {
        this.adr = adr;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

}