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
    @JoinTable(name = "VEICOLI_TRASPORTO_REQUISITO_VEICOLO_LINK",
            joinColumns = @JoinColumn(name = "VEICOLI_TRASPORTO_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "REQUISITO_VEICOLO_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<RequisitoVeicolo> caratteristicheAdr;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "AZIENDA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sede azienda;

    @Column(name = "CISTERNA_ADR")
    private Boolean cisternaAdr;

    public Sede getAzienda() {
        return azienda;
    }

    public void setAzienda(Sede azienda) {
        this.azienda = azienda;
    }

    public Boolean getCisternaAdr() {
        return cisternaAdr;
    }

    public void setCisternaAdr(Boolean cisternaAdr) {
        this.cisternaAdr = cisternaAdr;
    }

    public List<RequisitoVeicolo> getCaratteristicheAdr() {
        return caratteristicheAdr;
    }

    public void setCaratteristicheAdr(List<RequisitoVeicolo> caratteristicheAdr) {
        this.caratteristicheAdr = caratteristicheAdr;
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