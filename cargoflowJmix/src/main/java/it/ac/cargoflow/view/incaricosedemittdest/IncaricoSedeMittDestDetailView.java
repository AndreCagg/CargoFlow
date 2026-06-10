package it.ac.cargoflow.view.incaricosedemittdest;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "incarico-sede-mitt-dests/:id", layout = MainView.class)
@ViewController(id = "IncaricoSedeMittDest.detail")
@ViewDescriptor(path = "incarico-sede-mitt-dest-detail-view.xml")
@EditedEntityContainer("incaricoSedeMittDestDc")
public class IncaricoSedeMittDestDetailView extends StandardDetailView<IncaricoSedeMittDest> {
    @ViewComponent
    private DateTimePicker dalField;

    @ViewComponent
    private DateTimePicker alField;

    @Autowired
    private EntityStates es;

    @Autowired
    private DataManager dm;

    @Autowired
    private ViewValidation validation;

    @ViewComponent
    private EntityPicker sede_destinatarioField;

    @ViewComponent
    private EntityPicker sede_mittenteField;

    @ViewComponent
    private HorizontalLayout statoButtonsPanel;

    @ViewComponent
    private DataGrid statoDataGrid;

    @ViewComponent
    private ComboBox statoComboBox;

    private Logger log = LoggerFactory.getLogger(IncaricoSedeMittDestDetailView.class);

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        this.dalField.setMax(LocalDateTime.now());
        this.alField.setMax(LocalDateTime.now());
        IncaricoSedeMittDest sedi = this.getEditedEntity();
        if (this.es.isNew(sedi)) {
            this.alField.setVisible(false);
        }

        if (this.es.isNew(this.getEditedEntity())) {
            this.statoButtonsPanel.setVisible(false);
            this.statoDataGrid.setVisible(false);
        } else {
            this.statoComboBox.setVisible(false);
        }
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        ValidationErrors errors = new ValidationErrors();

        IncaricoSedeMittDest presave = this.getEditedEntity();

        if (presave.getDal() == null) {
            presave.setDal(LocalDateTime.now());
        }

        if (presave.getAl() != null && presave.getDal().isAfter(presave.getAl())) {
            errors.add(Costants.INTERVALLO_NON_VALIDO);
        }

        if (this.sede_mittenteField.getValue().equals(this.sede_destinatarioField.getValue())) {
            errors.add(Costants.STESSO_MITT_DEST);
            this.setInvalidSedeMittDest(true);
        }

        if (!errors.isEmpty()) {
            this.validation.showValidationErrors(errors);
            event.preventSave();
            return;
        }

        IncaricoSedeMittDest entity = presave;

        IncaricoSedeMittDest ismd = this.dm.load(IncaricoSedeMittDest.class)
                .query("select ismd from IncaricoSedeMittDest ismd where ismd.incarico.id = :id order by ismd.createdDate desc")
                .parameter("id", entity.getIncarico().getId())
                .optional().orElse(null);

        if (ismd != null) {
            IncaricoSedeMittDest ismddc = event.getDataContext().merge(ismd);
            ismddc.setAl(LocalDateTime.now());
        }

        if ( this.statoComboBox.getValue() != null) {
            String stato = this.statoComboBox.getValue().toString();
            StatoVariazioneSedi svs = this.dm.create(StatoVariazioneSedi.class);
            StatoVariazioneSedi svsdc = event.getDataContext().merge(svs);
            svsdc.setStato(Stato.valueOf(stato));
            svsdc.setIncarico(ismd.getIncarico());
            svsdc.setSedeMittDest(entity);
            svsdc.setDataStato(LocalDateTime.now());

            if(entity.getStato()!=null) {
                entity.getStato().add(svsdc);
            }else{
                entity.setStato(new ArrayList<>(List.of(svsdc)));
            }

        }
    }

    @Subscribe("sede_mittenteField")
    public void onSede_mittenteFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Cliente>, Cliente> event) {
        Cliente mitt = event.getSource().getValue();
        Cliente dest = (Cliente) this.sede_destinatarioField.getValue();

        if (mitt != null && dest != null && mitt.equals(dest)) {
            this.setInvalidSedeMittDest(true);
        } else {
            this.setInvalidSedeMittDest(false);
        }
    }

    @Subscribe("sede_destinatarioField")
    public void onSede_destinatarioFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Cliente>, Cliente> event) {
        Cliente mitt = (Cliente) this.sede_mittenteField.getValue();
        Cliente dest = event.getSource().getValue();

        if (mitt != null && dest != null && mitt.equals(dest)) {
            this.setInvalidSedeMittDest(true);
        } else {
            this.setInvalidSedeMittDest(false);
        }
    }

    private void setInvalidSedeMittDest(Boolean invalid) {
        this.sede_mittenteField.setInvalid(invalid);
        this.sede_destinatarioField.setInvalid(invalid);

        if (invalid) {
            this.sede_mittenteField.setErrorMessage(Costants.STESSO_MITT_DEST);
            this.sede_destinatarioField.setErrorMessage(Costants.STESSO_MITT_DEST);
        }
    }
}