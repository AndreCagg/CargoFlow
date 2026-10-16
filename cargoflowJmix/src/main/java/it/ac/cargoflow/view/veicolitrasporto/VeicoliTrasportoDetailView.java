package it.ac.cargoflow.view.veicolitrasporto;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.action.list.AddAction;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.formlayout.JmixFormLayout;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.JmixNumberField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.app.AziendaSedeContext;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.disposizionicisternaadr.DisposizioniCisternaADRListView;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Route(value = "veicoli-trasportoes/:id", layout = MainView.class)
@ViewController(id = "VeicoliTrasporto.detail")
@ViewDescriptor(path = "veicoli-trasporto-detail-view.xml")
@EditedEntityContainer("veicoliTrasportoDc")
public class VeicoliTrasportoDetailView extends StandardDetailView<VeicoliTrasporto> {
    @Autowired
    private MetadataTools metadataTools;
    @ViewComponent
    private DataContext dc;
    @Autowired
    private AziendaSedeContext asc;
    @ViewComponent
    private VerticalLayout adrVbox;
    @ViewComponent
    private JmixCheckbox adrField;
    @ViewComponent
    private VerticalLayout certBox;
    @ViewComponent
    private VerticalLayout disposizCisternaBox;
    @ViewComponent
    private JmixSelect<TipoVeicolo> tipologiaField;
    @ViewComponent
    private JmixFormLayout adrForm;
    @ViewComponent
    private JmixCheckbox pannelliAdrField;
    @ViewComponent
    private JmixCheckbox etichetteAdrField;
    @ViewComponent
    private JmixCheckbox borsaEqAdrField;
    @ViewComponent
    private JmixNumberField kgEstintoreField;
    @ViewComponent
    private EntityComboBox<VeicoloTrasportoADRGerarchia> codiceCisternaField;
    @ViewComponent
    private JmixNumberField pressioneCalcoloField;
    @ViewComponent
    private JmixNumberField pressioneProvaField;
    @ViewComponent
    private CollectionPropertyContainer<CertificatiVeicoloADR> certificatiDc;
    @ViewComponent
    private CollectionPropertyContainer<DisposizioniCisternaADR> disposizioniCisternaDc;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        adrVisibile(adrField.getValue());
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        getEditedEntity().setSede(asc.getSede());
    }

    @Subscribe("adrField")
    public void onAdrFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        adrVisibile(event.getValue());
    }

    private void adrVisibile(boolean v){
        adrVbox.setVisible(v);
        certBox.setVisible(v);
        disposizCisternaBox.setVisible(v);

        TipoVeicolo tv = tipologiaField.getValue();
        disposizCisternaBox.setVisible(tv != null && tv.equals(TipoVeicolo.CISTERNA_PORTACISTERNA));

        if(!v){
            tipologiaField.setValue(null);
            pannelliAdrField.setValue(false);
            etichetteAdrField.setValue(false);
            borsaEqAdrField.setValue(false);
            kgEstintoreField.setValue(0D);

            pulisciCampiCisterna();
            certificatiDc.getMutableItems().clear();
        }
    }

    @Subscribe("tipologiaField")
    public void onTipologiaFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<TipoVeicolo>, TipoVeicolo> event) {
        if(!event.isFromClient())
            return;

        boolean cisterna = event.getValue().equals(TipoVeicolo.CISTERNA_PORTACISTERNA);
        TipoVeicolo old = event.getOldValue();

        disposizCisternaBox.setVisible(cisterna);
        pressioneCalcoloField.setVisible(cisterna);
        pressioneProvaField.setVisible(cisterna);
        codiceCisternaField.setVisible(cisterna);

        if(old != null && old.equals(TipoVeicolo.CISTERNA_PORTACISTERNA))
            pulisciCampiCisterna();
    }

    private void pulisciCampiCisterna(){
        codiceCisternaField.setValue(null);
        pressioneCalcoloField.setValue(0D);
        pressioneProvaField.setValue(0D);
        disposizioniCisternaDc.getMutableItems().clear();
    }
}