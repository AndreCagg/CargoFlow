package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.DataGridColumn;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import io.jmix.groupgridflowui.component.GroupDataGrid;
import io.jmix.groupgridflowui.component.renderer.GroupDataGridColumnComponentRenderer;
import it.ac.cargoflow.app.AziendaSedeContext;
import it.ac.cargoflow.app.ColliADRBean;
import it.ac.cargoflow.app.IncaricoBean;
import it.ac.cargoflow.app.StatoMovimentoBean;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.cliente.ClienteDetailView;
import it.ac.cargoflow.view.colliadr.ColliADRDetailView;
import it.ac.cargoflow.view.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Route(value = "incaricoes/:id", layout = MainView.class)
@ViewController(id = "Incarico.detail")
@ViewDescriptor(path = "incarico-detail-view.xml")
@EditedEntityContainer("incaricoDc")
public class IncaricoDetailView extends StandardDetailView<Incarico> {

    @Autowired
    private DataManager dm;

    @Autowired
    private DialogWindows dialogWindows;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private EntityStates es;

    @ViewComponent
    private CollectionLoader<Cliente> clientiDl;

    @ViewComponent
    private EntityComboBox<Cliente> mittenteField;

    @ViewComponent
    private EntityComboBox<Cliente> destinatarioField;

    @ViewComponent
    private TextField ldvField;

    @ViewComponent
    private DatePicker data_consegna_previstaField;

    @ViewComponent
    private JmixCheckbox contrassegnoField;

    @ViewComponent
    private EntityComboBox contrassegno_tipoField;

    @ViewComponent
    private TextField contrassegno_valoreField;

    @ViewComponent
    private EntityComboBox<Cliente> sedeMitt;

    @ViewComponent
    private EntityComboBox<Cliente> sedeDest;

    @ViewComponent
    private HorizontalLayout sedeMittDest;

    @ViewComponent
    private DataGrid sedi_mitt_destDataGrid;

    @ViewComponent
    private HorizontalLayout sedi_mitt_destButtonsPanel;

    @ViewComponent
    private DataContext dc;

    private Logger log = LoggerFactory.getLogger(IncaricoDetailView.class);

    @Autowired
    private ViewValidation validation;

    @Autowired
    private AziendaSedeContext asc;

    @Autowired
    private CurrentAuthentication auth;
    @ViewComponent
    private CollectionPropertyContainer<IncaricoFasciaOraria> fasceOrarieDc;
    @ViewComponent
    private DataGrid<IncaricoFasciaOraria> fasceOrarieDataGrid;
    @ViewComponent
    private JmixCheckbox ritiroField;
    @ViewComponent
    private H4 txtSediConsegna;
    @ViewComponent
    private JmixCheckbox nonConsegnare;
    @ViewComponent
    private DataGrid<Merce> merceDataGrid;
    @ViewComponent
    private CollectionPropertyContainer<Merce> merceDc;
    @Autowired
    private ColliADRBean cb;
    @Autowired
    private UiComponents ui;
    @ViewComponent
    private CollectionPropertyContainer<IncaricoSedeMittDest> sedi_mitt_destDc;
    @ViewComponent
    private Span stato;
    @Autowired
    private IncaricoBean ib;
    @Autowired
    private StatoMovimentoBean smb;
    @ViewComponent
    private VerticalLayout movimentiBox;

    @Subscribe("ldvField")
    public void onLdvFieldValueChange(final AbstractField.ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        if (this.ldvField.isInvalid() && this.existsLdv(event.getSource().getValue()) == null) {
            this.ldvField.setInvalid(false);
        }
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {

        Incarico incarico = this.getEditedEntity();
        IncaricoSedeMittDest ismd = this.dataManager.create(IncaricoSedeMittDest.class);
        StatoVariazioneSedi svs = this.dataManager.create(StatoVariazioneSedi.class);

        ValidationErrors errors = new ValidationErrors();
        if(this.sedeMitt!=null && this.sedeDest!=null && this.sedeMitt.equals(this.sedeDest)){
            errors.add(Costants.STESSO_MITT_DEST);
            this.setInvalidSedeMittDest(true);
        }else {

            if (this.es.isNew(incarico)) {

                if (this.existsLdv(this.ldvField.getValue()) != null) {
                    errors.add(Costants.INCARICO_ESISTENTE);
                    this.ldvField.setInvalid(true);
                    this.ldvField.setErrorMessage(Costants.INCARICO_ESISTENTE);
                } else {

                    ismd.setSede_mittente(this.sedeMitt.getValue() != null ? (Cliente) this.sedeMitt.getValue() : incarico.getMittente());
                    ismd.setSede_destinatario(this.sedeDest.getValue() != null ? (Cliente) this.sedeDest.getValue() : incarico.getDestinatario());
                    ismd.setDal(LocalDateTime.now());
                    ismd.setAutorizzazione(Costants.GENESIS_EVENT);
                    ismd.setIncarico(incarico);

                    StatoVariazioneSedi svsDC = this.dc.merge(svs);

                    svsDC.setSedeMittDest(ismd);
                    svsDC.setIncarico(ismd.getIncarico());
                    svsDC.setStato(StatoIntegrazione.VALIDO);
                    svsDC.setDataStato(LocalDateTime.now());

                    ismd.setStato(List.of(svsDC));

                    incarico.setSedi_mitt_dest(List.of(ismd));
                }

                Movimenti mdc = this.dc.create(Movimenti.class);
                incarico.setAzienda(asc.getAzienda());
                mdc.setDescrizione(StatoMovimenti.DISPONIBILE_MAGAZZINO);
                mdc.setIncarico(getEditedEntity());

                if (incarico.getMovimenti() == null) {
                    incarico.setMovimenti(new ArrayList<>());
                }

                incarico.getMovimenti().add(mdc);
            }
        }

        if(!errors.isEmpty()){
            validation.showValidationErrors(errors);
            event.preventSave();
            return;
        }
    }

    @Subscribe("sedeMitt")
    public void onSedeMittComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Cliente>, Cliente> event) {
        Cliente mitt = event.getSource().getValue();
        Cliente dest = (Cliente) this.sedeDest.getValue();

        boolean b = mitt!=null && dest!=null && mitt.equals(dest);

        this.setInvalidSedeMittDest(b);

        if (ritiroField.getValue()){
            eliminaFasceOrarie(false);
            aggiornaFasceOrarie(mitt != null ? mitt : mittenteField.getValue(), false);
        }
    }

    @Subscribe("sedeDest")
    public void onSedeDestComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Cliente>, Cliente> event) {
        Cliente dest = event.getSource().getValue();
        Cliente mitt = (Cliente) this.sedeMitt.getValue();

        boolean b = mitt!=null && dest!=null && mitt.equals(dest);

        this.setInvalidSedeMittDest(b);

        if (!b){
            eliminaFasceOrarie(true);
        }

        aggiornaFasceOrarie(dest != null ? dest : destinatarioField.getValue(), true);

    }

    private void setInvalidSedeMittDest(Boolean invalid){
        this.sedeMitt.setInvalid(invalid);
        this.sedeDest.setInvalid(invalid);

        if(invalid) {
            this.sedeMitt.setErrorMessage(Costants.STESSO_MITT_DEST);
            this.sedeDest.setErrorMessage(Costants.STESSO_MITT_DEST);
        }
    }

    @Subscribe(id = "contrassegnoField", subject = "clickListener")
    public void onContrassegnoFieldClick(final ClickEvent<JmixCheckbox> event) {
        if (this.contrassegnoField.getValue()) {
            this.enableContrassegno(true);
        } else {
            this.enableContrassegno(false);
        }
    }

    @Subscribe("mittenteField")
    public void onMittenteFieldCustomValueSet(
            ComboBoxBase.CustomValueSetEvent<?> event) {

        String ragioneSociale = event.getDetail();

        Cliente cliente = dataManager.create(Cliente.class);
        cliente.setRagioneSociale(ragioneSociale);

        DialogWindow<ClienteDetailView> dialog =
                dialogWindows.detail(this, Cliente.class)
                        .withViewClass(ClienteDetailView.class)
                        .editEntity(cliente)
                        .build();

        dialog.addAfterCloseListener(closeEvent -> {
            if (closeEvent.closedWith(StandardOutcome.SAVE)) {

                Cliente salvato = dialog.getView().getEditedEntity();

                clientiDl.load();

                mittenteField.setValue(salvato);
            } else {
                mittenteField.clear();
            }
        });

        dialog.open();
    }

    @Subscribe("destinatarioField")
    public void onDestinatarioFieldCustomValueSet(
            ComboBoxBase.CustomValueSetEvent<?> event) {

        String ragioneSociale = event.getDetail();

        Cliente cliente = dataManager.create(Cliente.class);
        cliente.setRagioneSociale(ragioneSociale);

        DialogWindow<ClienteDetailView> dialog =
                dialogWindows.detail(this, Cliente.class)
                        .withViewClass(ClienteDetailView.class)
                        .editEntity(cliente)
                        .build();

        dialog.addAfterCloseListener(closeEvent -> {
            if (closeEvent.closedWith(StandardOutcome.SAVE)) {

                Cliente salvato = dialog.getView().getEditedEntity();

                clientiDl.load();

                destinatarioField.setValue(salvato);
            } else {
                destinatarioField.clear();
            }
        });

        dialog.open();
    }

    private void aggiornaClienti() {
        Cliente mitt = this.mittenteField.getValue();
        Cliente dest = this.destinatarioField.getValue();

        this.clientiDl.setParameter("currentMittenteId", mitt != null ? mitt.getId() : mitt);
        this.clientiDl.setParameter("currentDestinatarioId", dest != null ? dest.getId() : dest);

        this.clientiDl.load();
    }

    @Subscribe("mittenteField")
    public void onMittenteFieldComponentValueChange(final BlurNotifier.BlurEvent<EntityComboBox<Cliente>> event) {
        this.aggiornaClienti();
    }

    private Incarico existsLdv(String ldv) {
        Incarico i = this.dm.load(Incarico.class)
                .query("select i from Incarico i where i.ldv = :ldv")
                .parameter("ldv", ldv)
                .optional().orElse(null);

        return i;
    }

    @Subscribe("ldvField")
    public void onLdvFieldBlur(final BlurNotifier.BlurEvent<TypedTextField<String>> event) {
        if (!this.es.isNew(this.getEditedEntity())) return;

        Incarico i = this.existsLdv(event.getSource().getValue());

        if (i != null) {
            this.ldvField.setInvalid(true);
            this.ldvField.setErrorMessage(Costants.INCARICO_ESISTENTE);
            return;
        }

        this.ldvField.setInvalid(false);
    }

    @Subscribe("destinatarioField")
    public void onDestinatarioFieldComponentValueChange(final BlurNotifier.BlurEvent<EntityComboBox<Cliente>> event) {
        this.aggiornaClienti();
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        this.aggiornaClienti();
        this.data_consegna_previstaField.setMin(LocalDate.now());
        this.enableContrassegno(false);
        Incarico giaCreato = this.getEditedEntity();
        boolean isnew = this.es.isNew(giaCreato);
        movimentiBox.setVisible(!isnew);

        String statoStr = "", statoTheme = "";

        if (isnew) {
            this.sedeMittDest.setVisible(true);
            this.sedi_mitt_destDataGrid.setVisible(false);
            this.sedi_mitt_destButtonsPanel.setVisible(false);
            txtSediConsegna.setVisible(false);

            //TODO lo stato di bozza o disponibile magazzino si decide in base al fatto se adr e tutte le cose sono valide o meno
            statoStr = StatoMovimenti.DISPONIBILE_MAGAZZINO.toString();
            statoTheme = "badge primary";
        }else{
            StatoMovimenti sm = ib.ultimoMovimento(getEditedEntity());
            statoStr = sm.name();
            statoTheme = "badge "+smb.getColore(sm);
        }

        stato.setText(statoStr);
        stato.getElement().getThemeList().add(statoTheme);

        nonConsegnare.setVisible(ritiroField.getValue());

        DataGridColumn<IncaricoFasciaOraria> fo = fasceOrarieDataGrid.getColumnByKey("giorni");
        fo.setRenderer(new TextRenderer<>(item -> {
            List<String> giorni = new ArrayList<>();
            if (Boolean.TRUE.equals(item.getLun())) giorni.add("Lun");
            if (Boolean.TRUE.equals(item.getMar())) giorni.add("Mar");
            if (Boolean.TRUE.equals(item.getMer())) giorni.add("Mer");
            if (Boolean.TRUE.equals(item.getGio())) giorni.add("Gio");
            if (Boolean.TRUE.equals(item.getVen())) giorni.add("Ven");
            if (Boolean.TRUE.equals(item.getSab())) giorni.add("Sab");
            if (Boolean.TRUE.equals(item.getDom())) giorni.add("Dom");

            return giorni.stream().collect(Collectors.joining(", "));
        }));

        DataGridColumn<IncaricoFasciaOraria> tipo = fasceOrarieDataGrid.getColumnByKey("tipo");

        tipo.setRenderer(new TextRenderer<>(item -> Boolean.TRUE.equals(item.getConsegna()) ? "CONSEGNA" : "RITIRO"));


        merceDataGrid.getColumnByKey("adr").setRenderer(new ComponentRenderer<>(this::createADRLabel));
        merceDataGrid.getColumnByKey("fragile").setRenderer(new ComponentRenderer<>(obj -> {
            Icon icon = ui.create(Icon.class);

            if(obj.getFragile()){
                icon.setIcon(VaadinIcon.CHECK);
            }else{
                icon.setIcon(VaadinIcon.CLOSE_SMALL);
            }

            return icon;
        }));
        merceDataGrid.getColumnByKey("epal").setRenderer(new ComponentRenderer<>(obj -> {
            Icon icon = ui.create(Icon.class);

            if(obj.getEpal()){
                icon.setIcon(VaadinIcon.CHECK);
            }else{
                icon.setIcon(VaadinIcon.CLOSE_SMALL);
            }

            return icon;
        }));

        List<IncaricoSedeMittDest> sedi = sedi_mitt_destDc.getItems().stream()
                .filter(this::isValida)
                .sorted(Comparator.comparing(IncaricoSedeMittDest::getDal,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        if(!sedi.isEmpty()){
            sedeMitt.setValue(sedi.getLast().getSede_mittente());
            sedeDest.setValue(sedi.getLast().getSede_destinatario());
        }else{
            sedeMitt.setVisible(false);
            sedeDest.setVisible(false);
        }

        sedi_mitt_destDataGrid.getColumnByKey("stato").setRenderer(new ComponentRenderer<>(obj -> {
            IncaricoSedeMittDest i = (IncaricoSedeMittDest) obj;

            Span s = ui.create(Span.class);

            s.setText(i.getStato().getLast().getStato().toString());
            if(i.getStato().getLast().getStato().equals(StatoIntegrazione.VALIDO)){
                s.getElement().getThemeList().add("badge success");
            }else{
                s.getElement().getThemeList().add("badge warning");
            }


            return s;
        }));
    }

    private Span createADRLabel(Merce obj) {
        Span s = new Span();

        ElementoADR elemento = obj.getElementoAdr();
        boolean adr = elemento != null;

        if (!adr)
            return s;

        // Default per merce ADR rilevata
        dc.merge(obj).setStatoAdr(StatoADR.VALIDO);

        TipoImballaggio ti = obj.getTipoImball();
        boolean isTermo = ti.equals(TipoImballaggio.TERMO);
        boolean isCombinato = ti.equals(TipoImballaggio.COMBINATO);
        boolean isCat0 = elemento.getCategoriaTrasporto().equals(CatTrasporto.CAT0);

        // ==========================================
        // 1. VERIFICA REGIME EQ (Quantità Esenti - ADR 3.5)
        // ==========================================
        if (obj.getUmAdr().equals(UM.G) || obj.getUmAdr().equals(UM.ML)) {
            Eq eq = elemento.getQuantitaEsenti();
            if (eq != null && eq.getCodice() != 0) {

                boolean internoEqVietato = Boolean.TRUE.equals(eq.getVietatoInterno());
                boolean esternoEqVietato = Boolean.TRUE.equals(eq.getVietatoEsterno());

                if (!internoEqVietato && !esternoEqVietato) {
                    double nettoInterno = obj.getNettoInterno();
                    double nettoTotaleCollo = obj.getNettoInterno() * obj.getConfezioniIntAdr();

                    boolean qtaInternaOk = eq.getQtaInterno() != null && nettoInterno <= eq.getQtaInterno();
                    boolean qtaEsternaOk = eq.getQtaEsterno() != null && nettoTotaleCollo <= eq.getQtaEsterno();

                    if (qtaInternaOk && qtaEsternaOk) {
                        s.setText("REGIME EQ (E" + eq.getCodice() + ")");
                        s.getElement().getThemeList().add("badge success");
                        dc.merge(obj).setStatoAdr(StatoADR.VALIDO);
                        return s;
                    }
                }
            }
        }

        // ==========================================
        // 2. VERIFICA REGIME LQ (Quantità Limitate - ADR 3.4)
        // ==========================================
        LQ lq = elemento.getQuantitaLimitate();
        if (lq != null && lq.getLq() != 0) {

            // --- A. BLOCCO STRUTTURALE/TASSAZIONE NORMATIVA (Es. LQ3 su Termo) ---
            // Se c'è un divieto di legge assoluto non legato alla quantità, blocca con NON CONFORME.
            if (lq.getLq() == 3 && isTermo) {
                s.setText("ADR NON CONFORME (LQ3 vietato su termo)");
                s.getElement().getThemeList().add("badge error");
                dc.merge(obj).setStatoAdr(StatoADR.NON_VALIDO);
                return s;
            }

            // --- B. VERIFICA AUTORIZZAZIONE ED IDONEITÀ QUANTITÀ (INTERNO ED ESTERNO) ---
            boolean lqValido = false;

            if (isCombinato) {
                // Controlla se la modalità combinata è vietata a prescindere
                boolean combinatoAutorizzato = !Boolean.TRUE.equals(lq.getCombinatoInternoNonAutorizzato())
                        && !Boolean.TRUE.equals(lq.getCombinatoColloNonAutorizzato());

                if (combinatoAutorizzato) {
                    // Controllo capienza recipiente interno
                    boolean internoOk = Boolean.TRUE.equals(lq.getCombinatoInternoIllimitato())
                            || (lq.getValoreCombinatiInterno() != null && obj.getNettoInterno() <= lq.getValoreCombinatiInterno());

                    // Controllo limite peso/massa collo
                    double limiteCollo = (lq.getValoreCombinatiCollo() != null && !Boolean.TRUE.equals(lq.getCombinatoColloIllimitato()))
                            ? lq.getValoreCombinatiCollo() : 30.0;

                    boolean colloOk = Boolean.TRUE.equals(lq.getCombinatoColloIllimitato())
                            || obj.getDimensioneConf() <= limiteCollo;

                    if (internoOk && colloOk) {
                        lqValido = true;
                    }
                }
            } else if (isTermo) {
                // Controlla se la modalità pellicola/termo è vietata a prescindere
                boolean termoAutorizzato = !Boolean.TRUE.equals(lq.getPellicolaInternoNonAutorizzato())
                        && !Boolean.TRUE.equals(lq.getPellicolaColloNonAutorizzato());

                if (termoAutorizzato) {
                    // Controllo capienza recipiente interno
                    boolean internoOk = Boolean.TRUE.equals(lq.getPellicolaInternoIllimitato())
                            || (lq.getValorePellicolaInterno() != null && obj.getNettoInterno() <= lq.getValorePellicolaInterno());

                    // Controllo limite peso/massa collo
                    double limiteCollo = (lq.getValorePellicolaCollo() != null && !Boolean.TRUE.equals(lq.getPellicolaColloIllimitato()))
                            ? lq.getValorePellicolaCollo() : 20.0;

                    boolean colloOk = Boolean.TRUE.equals(lq.getPellicolaColloIllimitato())
                            || obj.getDimensioneConf() <= limiteCollo;

                    if (internoOk && colloOk) {
                        lqValido = true;
                    }
                }
            }

            // Se tutti i parametri rientrano nelle soglie autorizzate per LQ
            if (lqValido) {
                s.setText("REGIME LQ");
                s.getElement().getThemeList().add("badge warning");
                dc.merge(obj).setStatoAdr(StatoADR.VALIDO);
                return s;
            }
        }

        // ==========================================
        // 3. FALLBACK: ESENZIONE 1.1.3.6 O ADR COMPLETO (Passo 4)
        // ==========================================
        // Se l'imballo non è autorizzato per LQ causa superamento quantità,
        // la merce scala automaticamente alla verifica dei 1000 Punti o ADR Completo.
        if (!isCat0) {
            return esenzioneParziale(s, obj);
        } else {
            s.setText("ADR COMPLETO");
            s.getElement().getThemeList().add("badge warning");
            dc.merge(obj).setStatoAdr(StatoADR.VALIDO);
            return s;
        }
    }

    @Subscribe("ritiroField")
    public void onRitiroFieldValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        log.info("ritiro: {} -> {} (fromClient={})",
                event.getOldValue(), event.getValue(), event.isFromClient());
        if (!event.isFromClient()) {
            new Exception("trace ritiro").printStackTrace();
        }
    }

    private Span esenzioneParziale(Span s, Merce obj) {
        if (esenzioneParziale_(obj)) {
            s.setText("ADR ESENTE 1.1.3.6");
            s.getElement().getThemeList().add("badge warning");
            return s;
        } else {
            s.setText("ADR COMPLETO");
            s.getElement().getThemeList().add("badge warning");
            return s;
        }
    }

    private boolean esenzioneParziale_(Merce obj) {
        // Quantità totale per il calcolo dei punti (Litri o Kg)
        double quantitaTotale = obj.getNettoInterno() * obj.getConfezioniIntAdr();

        // Recupera il MOLTIPLICATORE (non l'ID) in base alla Categoria di Trasporto ADR 1.1.3.6.4:
        // Cat 1 -> 50, Cat 2 -> 3, Cat 3 -> 1, Cat 4 -> 0
        int moltiplicatore = obj.getElementoAdr().getCategoriaTrasporto().getId();

        return (quantitaTotale * moltiplicatore) <= 1000;
    }

    private boolean almenoUnADR(){
        return merceDc.getItems().stream().anyMatch(obj -> obj.getElementoAdr()!=null);
    }

    private void enableContrassegno(Boolean en) {
        this.contrassegno_tipoField.setVisible(en);
        this.contrassegno_valoreField.setVisible(en);
    }

    @Subscribe(id = "sedi_mitt_destDc", target = Target.DATA_CONTAINER)
    public void onIncaricoSediDcCollectionChange(final CollectionContainer.CollectionChangeEvent<IncaricoSedeMittDest> event) {

        if (event.getChangeType() == CollectionChangeType.REMOVE_ITEMS) {
            Incarico entity = this.getEditedEntity();
            List<IncaricoSedeMittDest> rimossi = (List<IncaricoSedeMittDest>) event.getChanges();

            for (IncaricoSedeMittDest item : rimossi) {
                log.info("Elemento rimosso dal contenitore: {}", item.getId());
                
                List<IncaricoSedeMittDest> storiaSedi = this.dm.load(IncaricoSedeMittDest.class)
                        .query("select i from IncaricoSedeMittDest i where i.incarico.ldv = :ldv order by i.createdDate ASC")
                        .parameter("ldv", entity.getLdv())
                        .list();

                int index = -1;
                for (int i = 0; i < storiaSedi.size(); i++) {
                    if (storiaSedi.get(i).getId().equals(item.getId())) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    continue;
                }

                boolean isUltimo = (index == storiaSedi.size() - 1);

                if (isUltimo) {
                    if (index > 0) {
                        IncaricoSedeMittDest precedente = storiaSedi.get(index - 1);
                        IncaricoSedeMittDest precedenteDC = this.dc.merge(precedente);
                        precedenteDC.setAl(null);
                    }
                } else {
                    IncaricoSedeMittDest successivo = storiaSedi.get(index + 1);

                    if (index > 0) {
                        IncaricoSedeMittDest precedente = storiaSedi.get(index - 1);
                        IncaricoSedeMittDest precedenteDC = this.dc.merge(precedente);

                        precedenteDC.setAl(successivo.getDal());
                    }
                }
            }
        }

        List<IncaricoSedeMittDest> sedi = sedi_mitt_destDc.getItems().stream()
                .filter(this::isValida)
                .sorted(Comparator.comparing(IncaricoSedeMittDest::getDal,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        boolean b = sedi.size()>1;
        sedeMitt.setVisible(b);
        sedeDest.setVisible(b);

        if(b){
            sedeMitt.setValue(sedi.getLast().getSede_mittente());
            sedeDest.setValue(sedi.getLast().getSede_destinatario());
        }else{
            sedeMitt.setValue(null);
            sedeDest.setValue(null);
        }

        eliminaFasceOrarie(false);
        eliminaFasceOrarie(true);
        Cliente sm = sedeMitt.getValue(), sd = sedeDest.getValue();

        if(ritiroField.getValue()){
            aggiornaFasceOrarie(sm != null ? sm : mittenteField.getValue(), false);
            aggiornaFasceOrarie(sd != null ? sd : destinatarioField.getValue(), true);
        }else{
            aggiornaFasceOrarie(sd != null ? sd : destinatarioField.getValue(), true);
        }
    }

    private boolean isValida(IncaricoSedeMittDest s) {
        List<StatoVariazioneSedi> stati = s.getStato();
        if (stati == null || stati.isEmpty()) return false;

        StatoVariazioneSedi ultimo = stati.stream().filter(obj -> obj.getStato().equals(StatoIntegrazione.VALIDO)).max(Comparator.comparing(StatoVariazioneSedi::getDataStato)).orElse(null);
        return ultimo != null && ultimo.getStato().equals(StatoIntegrazione.VALIDO);
    }

    @Subscribe("destinatarioField")
    public void onDestinatarioFieldComponentValueChange1(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Cliente>, Cliente> event) {
        Cliente c = event.getValue();
        eliminaFasceOrarie(true);
        aggiornaFasceOrarie(c, true);
    }

    private void aggiornaFasceOrarie(Cliente c, boolean consegna){
        if(c!=null){
            List<FasciaOraria> lst = c.getFasceOrarie();
            if(!lst.isEmpty()){
                List<IncaricoFasciaOraria> ifolst = new ArrayList<>();

                for(FasciaOraria fo : lst){
                    if(consegna && Boolean.TRUE.equals(fo.getSolo_ritiro())) continue;

                    if(!consegna && Boolean.TRUE.equals(fo.getSolo_consegna())) continue;

                    IncaricoFasciaOraria ifo = dm.create(IncaricoFasciaOraria.class);
                    ifo = dc.merge(ifo);

                    ifo.setLun(fo.getLun());
                    ifo.setLun(fo.getLun());
                    ifo.setMar(fo.getMar());
                    ifo.setMer(fo.getMer());
                    ifo.setGio(fo.getGio());
                    ifo.setVen(fo.getVen());
                    ifo.setSab(fo.getSab());
                    ifo.setDom(fo.getDom());
                    ifo.setDalle(fo.getDalle());
                    ifo.setAlle(fo.getAlle());
                    ifo.setIncarico(this.getEditedEntity());
                    ifo.setConsegna(consegna);
                    ifo.setRitiro(!consegna);
                    ifolst.add(ifo);
                }

                fasceOrarieDc.getMutableItems().addAll(ifolst);
            }
        }
    }

    @Subscribe(id = "ritiroField", subject = "clickListener")
    public void onRitiroFieldClick(final ClickEvent<JmixCheckbox> event) {
        boolean b = event.getSource().getValue();
        DataGrid.Column<IncaricoFasciaOraria> ifo = fasceOrarieDataGrid.getColumnByKey("tipo");
        ifo.setVisible(b);

        Cliente sedeMitt = (Cliente) this.sedeMitt.getValue();
        if(b)
            aggiornaFasceOrarie(sedeMitt != null ? sedeMitt : mittenteField.getValue(), false);
        else{
            eliminaFasceOrarie(false);
            nonConsegnare.setValue(b);
        }

        nonConsegnare.setVisible(b);
    }

    @Subscribe("nonConsegnare")
    public void onNonConsegnareComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        Cliente sedeDest = this.sedeDest.getValue();

        eliminaFasceOrarie(true);

        if(!event.getValue()) {
            aggiornaFasceOrarie(sedeDest != null ? sedeDest : destinatarioField.getValue(), true);
        }
    }

    private void eliminaFasceOrarie(boolean consegna){
        List<IncaricoFasciaOraria> lst = fasceOrarieDc.getItems();
        List<IncaricoFasciaOraria> rimuovere = new ArrayList<>();

        for(IncaricoFasciaOraria f : lst){
            if(!consegna && Boolean.TRUE.equals(f.getRitiro())){
                rimuovere.add(f);
            }

            if(consegna && Boolean.TRUE.equals(f.getConsegna())){
                rimuovere.add(f);
            }
        }

        fasceOrarieDc.getMutableItems().removeAll(rimuovere);
    }

    @Subscribe("mittenteField")
    public void onMittenteFieldComponentValueChange1(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Cliente>, Cliente> event) {
        if(ritiroField.getValue()){
            eliminaFasceOrarie(false);
            aggiornaFasceOrarie(event.getValue(), false);
        }
    }

    /*@Subscribe(id = "creaCollo", subject = "clickListener")
    public void onCreaColloClick(final ClickEvent<JmixButton> event) {
        dialogWindows.detail(this, ColliADR.class)
                .withParentDataContext(dc)
                .withInitializer(collo -> {
                    collo.setNum(generaNumADR.get() + 1);
                })
                .withViewConfigurer(detail -> {
                    ((ColliADRDetailView) detail)
                            .setMerce(merceDc.getMutableItems().stream()
                                    .filter(m ->
                                            m.getColliADR() == null &&
                                                    m.getMerce_tipo().equals(TipoMerce.COLLO) && m.getElementoAdr()!=null).toList());
                })
                .withAfterCloseListener(close -> {
                    if(close.closedWith(StandardOutcome.SAVE)){
                        ColliADRDetailView view = (ColliADRDetailView) close.getView();
                        List<Merce> selezionata = view.getSelezionata();

                        colliBox.setVisible(true);

                        ColliADR collo = view.getEditedEntity();
                        collo.setColli(selezionata);
                        collo.setTipoImballaggio(view.getTipoImballaggio());
                        colliADRsDc.getMutableItems().add(collo);
                        colliADRsDataGrid.expandAll();

                        for(Merce m : selezionata){
                            dc.merge(m).setColliADR(collo);
                        }
                    }
                })
                .build()
                .open();
    }*/

    /*@Subscribe(id = "colliADRsDc", target = Target.DATA_CONTAINER)
    public void onColliADRsDcCollectionChange(final CollectionContainer.CollectionChangeEvent<ColliADR> e) {
        if (e.getChangeType() == CollectionChangeType.REMOVE_ITEMS) {
            for (ColliADR removed : e.getChanges()) {
                merceDc.getMutableItems().stream()
                        .filter(m -> m.getColliADR() != null
                                && m.getColliADR().getId().equals(removed.getId()))
                        .forEach(m -> dc.merge(m).setColliADR(null));
            }
        }

        if(colliADRsDc.getItems().isEmpty()){
            colliBox.setVisible(false);
        }
    }*/

    @Subscribe(id = "merceDc", target = Target.DATA_CONTAINER)
    public void onMerceDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<Merce> event) {
        if (event.getProperty().equals("colliADR")) return;

        //1. verifica se ce la merce in un collo. 2. stacca dal dc la relazione con m. 3. rimuovi m dalal lista colli. 4. assegna la nuova lista colli. 5. eventualmente rimuovi collo da cc e dc
        // regola da infilare nel cervelletto: se una entità va modificata in qualche modo allora va fatta managed

        Merce m = event.getItem();
        boolean eligibile = m.getElementoAdr() == null || m.getMerce_tipo().equals(TipoMerce.PALLET);
        if (!eligibile) return;

        /*colliADRsDc.getItems().stream()
                .filter(collo -> collo.getColli() != null && collo.getColli().contains(m))
                .findFirst()
                .ifPresent(collo -> {
                    Merce merceManaged = dc.merge(m);
                    ColliADR colloManaged = dc.merge(collo);

                    merceManaged.setColliADR(null);

                    List<Merce> aggiornata = new ArrayList<>(colloManaged.getColli());
                    aggiornata.remove(merceManaged);
                    colloManaged.setColli(aggiornata);

                    if (aggiornata.isEmpty()) {
                        colliADRsDc.getMutableItems().remove(colloManaged);
                        dc.remove(colloManaged);
                    }
                });*/
    }


}