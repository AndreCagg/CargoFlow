package it.ac.cargoflow.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "INCARICO", indexes = {
        @Index(name = "IDX_INCARICO_MITTENTE", columnList = "MITTENTE_ID"),
        @Index(name = "IDX_INCARICO_DESTINATARIO", columnList = "DESTINATARIO_ID"),
        @Index(name = "IDX_INCARICO_CHILD", columnList = "CHILD_ID"),
        @Index(name = "IDX_INCARICO_AZIENDA", columnList = "AZIENDA_ID"),
        @Index(name = "IDX_INCARICO_CONTRASSEGNO_TIPO", columnList = "CONTRASSEGNO_TIPO_ID")
})
@Entity
public class Incarico {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "LDV", nullable = false)
    @NotNull
    private String ldv;

    @Column(name = "DDT", length = 50)
    private String ddt;

    @Column(name = "DATA_CONSEGNA_PREVISTA", nullable = false)
    @NotNull
    private LocalDate data_consegna_prevista;

    @Column(name = "VALORE_DOGANALE")
    private Double valore_doganale;

    @Column(name = "RITIRO")
    private Boolean ritiro = false;

    @Column(name = "VALORE_ASSICURAZIONE")
    private Double valore_assicurazione;

    @NotNull
    @JoinColumn(name = "MITTENTE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente mittente;

    @NotNull
    @JoinColumn(name = "DESTINATARIO_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente destinatario;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "incarico")
    private List<Merce> merce;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "incarico")
    private List<IncaricoSedeMittDest> sedi_mitt_dest;

    @OnDelete(DeletePolicy.CASCADE)
    @JoinColumn(name = "CHILD_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Incarico child;

    @JoinColumn(name = "AZIENDA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Azienda azienda;

    @Column(name = "CONTRASSEGNO")
    private Boolean contrassegno = false;

    @Column(name = "CONTRASSEGNO_VALORE")
    private Double contrassegno_valore;

    @JoinColumn(name = "CONTRASSEGNO_TIPO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContrassegnoTipo contrassegno_tipo;

    @Column(name = "NOTE")
    @Lob
    private String note;

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

    public List<IncaricoSedeMittDest> getSedi_mitt_dest() {
        return sedi_mitt_dest;
    }

    public void setSedi_mitt_dest(List<IncaricoSedeMittDest> sedi_mitt_dest) {
        this.sedi_mitt_dest = sedi_mitt_dest;
    }

    public List<Merce> getMerce() {
        return merce;
    }

    public void setMerce(List<Merce> merce) {
        this.merce = merce;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ContrassegnoTipo getContrassegno_tipo() {
        return contrassegno_tipo;
    }

    public void setContrassegno_tipo(ContrassegnoTipo contrassegno_tipo) {
        this.contrassegno_tipo = contrassegno_tipo;
    }

    public Double getContrassegno_valore() {
        return contrassegno_valore;
    }

    public void setContrassegno_valore(Double contrassegno_valore) {
        this.contrassegno_valore = contrassegno_valore;
    }

    public Boolean getContrassegno() {
        return contrassegno;
    }

    public void setContrassegno(Boolean contrassegno) {
        this.contrassegno = contrassegno;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public Incarico getChild() {
        return child;
    }

    public void setChild(Incarico child) {
        this.child = child;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public Cliente getMittente() {
        return mittente;
    }

    public void setMittente(Cliente mittente) {
        this.mittente = mittente;
    }

    public Double getValore_assicurazione() {
        return valore_assicurazione;
    }

    public void setValore_assicurazione(Double valore_assicurazione) {
        this.valore_assicurazione = valore_assicurazione;
    }

    public Boolean getRitiro() {
        return ritiro;
    }

    public void setRitiro(Boolean ritiro) {
        this.ritiro = ritiro;
    }

    public Double getValore_doganale() {
        return valore_doganale;
    }

    public void setValore_doganale(Double valore_doganale) {
        this.valore_doganale = valore_doganale;
    }

    public LocalDate getData_consegna_prevista() {
        return data_consegna_prevista;
    }

    public void setData_consegna_prevista(LocalDate data_consegna_prevista) {
        this.data_consegna_prevista = data_consegna_prevista;
    }

    public String getDdt() {
        return ddt;
    }

    public void setDdt(String ddt) {
        this.ddt = ddt;
    }

    public String getLdv() {
        return ldv;
    }

    public void setLdv(String ldv) {
        this.ldv = ldv;
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