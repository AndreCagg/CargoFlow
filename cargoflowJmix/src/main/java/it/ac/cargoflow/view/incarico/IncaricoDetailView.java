package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.entity.Incarico;
import it.ac.cargoflow.entity.IncaricoSedeMittDest;
import it.ac.cargoflow.view.cliente.ClienteDetailView;
import it.ac.cargoflow.view.main.MainView;
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

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        Incarico incarico = this.getEditedEntity();
        IncaricoSedeMittDest ismd = this.dataManager.create(IncaricoSedeMittDest.class);

        ismd.setSede_mittente(this.sedeMitt.getValue()!=null ? (Cliente) this.sedeMitt.getValue() : incarico.getMittente());
        ismd.setSede_destinatario(this.sedeDest.getValue()!=null ? (Cliente) this.sedeDest.getValue() : incarico.getDestinatario());
        ismd.setDal(LocalDateTime.now());
        ismd.setAutorizzazione(Costants.GENESIS_EVENT);
        ismd.setIncarico(incarico);

        incarico.setSedi_mitt_dest(List.of(ismd));
    }

    @Subscribe(id = "contrassegnoField", subject = "clickListener")
    public void onContrassegnoFieldClick(final ClickEvent<JmixCheckbox> event) {
        if(this.contrassegnoField.getValue()){
            this.enableContrassegno(true);
        }else{
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

    private void aggiornaClienti(){
        Cliente mitt = this.mittenteField.getValue();
        Cliente dest = this.destinatarioField.getValue();

        this.clientiDl.setParameter("currentMittenteId", mitt!=null? mitt.getId() : mitt);
        this.clientiDl.setParameter("currentDestinatarioId", dest!=null? dest.getId() : dest);

        this.clientiDl.load();
    }

    @Subscribe("mittenteField")
    public void onMittenteFieldComponentValueChange(final BlurNotifier.BlurEvent<EntityComboBox<Cliente>> event) {
        this.aggiornaClienti();
    }

    @Subscribe("ldvField")
    public void onLdvFieldBlur(final BlurNotifier.BlurEvent<TypedTextField<String>> event) {
        Incarico i = this.dm.load(Incarico.class)
                .query("select i from Incarico i where i.ldv = :ldv")
                .parameter("ldv", event.getSource().getValue())
                .optional().orElse(null);

        if(i!=null){
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

        if(this.es.isNew(giaCreato)){
            this.sedeMittDest.setVisible(true);
            this.sedi_mitt_destDataGrid.setVisible(false);
            this.sedi_mitt_destButtonsPanel.setVisible(false);
        }else{
            this.sedeMittDest.setVisible(false);
        }
    }

    private void enableContrassegno(Boolean en){
        this.contrassegno_tipoField.setVisible(en);
        this.contrassegno_valoreField.setVisible(en);

        /*if(!en) {
            this.contrassegno_valoreField.setValue(null);
            this.contrassegno_tipoField.setValue(null);
        }*/
    }
}