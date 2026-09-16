package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.DataGridColumn;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.app.AziendaSedeContext;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.cliente.ClienteDetailView;
import it.ac.cargoflow.view.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private H3 txtSediConsegna;
    @ViewComponent
    private JmixCheckbox nonConsegnare;

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

                Movimenti m = this.dm.create(Movimenti.class);
                Movimenti mdc = this.dc.merge(m);
                incarico.setAzienda(asc.getAzienda());
                mdc.setSede(asc.getSede());
                mdc.setDescrizione(StatoMovimenti.INSERITO);
            }
        }

        if(!errors.isEmpty()){
            validation.showValidationErrors(errors);
            event.preventSave();
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

        if (this.es.isNew(giaCreato)) {
            this.sedeMittDest.setVisible(true);
            this.sedi_mitt_destDataGrid.setVisible(false);
            this.sedi_mitt_destButtonsPanel.setVisible(false);
            txtSediConsegna.setVisible(false);
        } else {
            this.sedeMittDest.setVisible(false);
        }

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
            if(!consegna && f.getRitiro()){
                rimuovere.add(f);
            }

            if(consegna && f.getConsegna()){
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
}