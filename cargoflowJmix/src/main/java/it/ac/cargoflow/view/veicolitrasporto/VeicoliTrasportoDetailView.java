package it.ac.cargoflow.view.veicolitrasporto;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.action.list.AddAction;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.disposizionicisternaadr.DisposizioniCisternaADRListView;
import it.ac.cargoflow.view.main.MainView;
import it.ac.cargoflow.view.trasportocolli.TrasportoColliListView;
import it.ac.cargoflow.view.trasportorinfusa.TrasportoRinfusaListView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Route(value = "veicoli-trasportoes/:id", layout = MainView.class)
@ViewController(id = "VeicoliTrasporto.detail")
@ViewDescriptor(path = "veicoli-trasporto-detail-view.xml")
@EditedEntityContainer("veicoliTrasportoDc")
public class VeicoliTrasportoDetailView extends StandardDetailView<VeicoliTrasporto> {
    @ViewComponent("caratteristicheAdrDataGrid.addColliAction")
    private AddAction<TrasportoColli> caratteristicheAdrDataGridAddColliAction;
    @ViewComponent("caratteristicheAdrDataGrid.addRinfusaAction")
    private AddAction<TrasportoRinfusa> caratteristicheAdrDataGridAddRinfusaAction;
    @ViewComponent
    private DataGrid<RequisitoVeicolo> caratteristicheAdrDataGrid;
    @Autowired
    private MetadataTools metadataTools;
    @ViewComponent
    private JmixCheckbox cisternaAdrField;
    @ViewComponent
    private CollectionPropertyContainer<RequisitoVeicolo> caratteristicheAdrDc;
    @ViewComponent
    private JmixCheckbox adrField;
    @ViewComponent
    private VerticalLayout caratteristiche;
    @ViewComponent
    private TypedTextField<Object> codCisterna;
    @ViewComponent("caratteristicheAdrDataGrid.addCisternaAction")
    private AddAction<RequisitoVeicolo> caratteristicheAdrDataGridAddCisternaAction;
    @ViewComponent
    private JmixButton disposizioniCisterna;
    @ViewComponent
    private JmixButton addColli;
    @ViewComponent
    private JmixButton addRinfusa;

    @Subscribe
    public void onInit(final InitEvent event) {
        caratteristicheAdrDataGridAddColliAction.setViewClass(TrasportoColliListView.class);
        caratteristicheAdrDataGridAddColliAction.setAfterCloseHandler(e->{
            if(e.closedWith(StandardOutcome.SELECT)){
                LookupView<?> lookupView = (LookupView<?>) e.getView();
                Collection<?> selezionati = lookupView.getLookupComponent().getSelectedItems();

                selezionati.forEach(item -> {
                    TrasportoColli entita = (TrasportoColli) item;
                    entita.setTipoRequisito(TipoRequisitiVeicolo.TRASPORTO_COLLI);
                });
            }
        });

        caratteristicheAdrDataGridAddRinfusaAction.setViewClass(TrasportoRinfusaListView.class);
        caratteristicheAdrDataGridAddRinfusaAction.setAfterCloseHandler(e->{
            if(e.closedWith(StandardOutcome.SELECT)){
                LookupView<?> lookupView = (LookupView<?>) e.getView();
                Collection<?> selezionati = lookupView.getLookupComponent().getSelectedItems();

                selezionati.forEach(item -> {
                    TrasportoRinfusa entita = (TrasportoRinfusa) item;
                    entita.setTipoRequisito(TipoRequisitiVeicolo.TRASPORTO_RINFUSA);
                });
            }
        });

        caratteristicheAdrDataGridAddCisternaAction.setViewClass(DisposizioniCisternaADRListView.class);
        caratteristicheAdrDataGridAddCisternaAction.setAfterCloseHandler(e->{
            if(e.closedWith(StandardOutcome.SELECT)){
                LookupView<?> lookupView = (LookupView<?>) e.getView();
                Collection<?> selezionati = lookupView.getLookupComponent().getSelectedItems();

                selezionati.forEach(item -> {
                    DisposizioniCisternaADR entita = (DisposizioniCisternaADR) item;
                    entita.setTipoRequisito(TipoRequisitiVeicolo.DISPOSIZIONI_CISTERNA);
                });
            }
        });

        caratteristicheAdrDataGrid.getColumnByKey("tipo")
                .setRenderer(new TextRenderer<>(obj -> {
                    return switch(obj){
                        case TrasportoColli a-> "Trasporto Colli";
                        case TrasportoRinfusa a-> "Trasporto Rinfusa";
                        case DisposizioniCisternaADR a-> "Disposizioni Cisterna";
                        default -> "";
                    };
                }));

        caratteristicheAdrDataGrid.getColumnByKey("instanceName")
                .setRenderer(new TextRenderer<>(metadataTools::getInstanceName));

        boolean b = adrField.getValue();
        cisternaAdrField.setVisible(b);

        boolean b2 = cisternaAdrField.getValue();

        caratteristiche.setVisible(b);
        codCisterna.setVisible(b2);
        disposizioniCisterna.setVisible(b2);
        addColli.setVisible(!b2);
        addRinfusa.setVisible(!b2);
    }

    @Subscribe("adrField")
    public void onAdrFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        boolean b = event.getValue();

        cisternaAdrField.setVisible(b);
        caratteristiche.setVisible(b);

        if(!b){
            cisternaAdrField.setValue(b);
            caratteristicheAdrDc.setItems(List.of());
        }
    }

    @Subscribe("cisternaAdrField")
    public void onCisternaAdrFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        boolean b = event.getValue();

        codCisterna.setVisible(b);
        disposizioniCisterna.setVisible(b);
        addColli.setVisible(!b);
        addRinfusa.setVisible(!b);
        if(!b){
            codCisterna.setValue("");


        }
    }
}