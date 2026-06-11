package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.model.CollectionChangeType;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.cliente.ClienteDetailView;
import it.ac.cargoflow.view.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private EntityPicker sedeMitt;

    @ViewComponent
    private EntityPicker sedeDest;

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

    @ViewComponent
    private EntityComboBox<Azienda> azienda;

    @ViewComponent
    private EntityComboBox<Sede> sede;

    @ViewComponent
    private CollectionLoader<Sede> sediDl;

    @Autowired
    private CurrentAuthentication auth;

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

                Sede s = null;
                User u = (User) this.auth.getUser();

                if(u.getSede()==null){
                    //deve aver selezionato una sede
                    if(this.sede.getValue()==null){
                        errors.add(Costants.SEDE_NON_PRESENTE);
                        this.sede.setInvalid(true);
                        this.sede.setErrorMessage(Costants.SEDE_NON_PRESENTE);
                    }else{
                        s = this.sede.getValue();
                    }
                }else{
                    s = u.getSede();
                }

                if(s!=null) {
                    mdc.setDescrizione(StatoMovimenti.INSERITO);
                    mdc.setSede(s);
                }
            }
        }

        if(!errors.isEmpty()){
            validation.showValidationErrors(errors);
            event.preventSave();
        }
    }

    @Subscribe("sedeMitt")
    public void onSedeMittComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Cliente>, Cliente> event) {
        Cliente mitt = event.getSource().getValue();
        Cliente dest = (Cliente) this.sedeDest.getValue();

        if(mitt!=null && dest!=null && mitt.equals(dest)){
            this.setInvalidSedeMittDest(true);
        }else{
            this.setInvalidSedeMittDest(false);
        }
    }

    @Subscribe("sedeDest")
    public void onSedeDestComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Cliente>, Cliente> event) {
        Cliente dest = event.getSource().getValue();
        Cliente mitt = (Cliente) this.sedeMitt.getValue();

        if(mitt!=null && dest!=null && mitt.equals(dest)){
            this.setInvalidSedeMittDest(true);
        }else{
            this.setInvalidSedeMittDest(false);
        }
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
        } else {
            this.sedeMittDest.setVisible(false);
        }
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

    @Subscribe("azienda")
    public void onAziendaComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Azienda>, Azienda> event) {
        Azienda a = this.azienda.getValue();
        this.sede.setValue(null);

        if (a != null){
            this.sediDl.setParameter("idAzienda", a.getId());
            this.sediDl.load();
        }
    }
}