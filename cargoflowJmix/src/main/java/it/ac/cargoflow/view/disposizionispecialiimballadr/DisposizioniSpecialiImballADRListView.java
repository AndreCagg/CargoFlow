package it.ac.cargoflow.view.disposizionispecialiimballadr;

import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.DisposizioniSpecialiImballADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "disposizioni-speciali-imball-adrs", layout = MainView.class)
@ViewController(id = "DisposizioniSpecialiImballADR.list")
@ViewDescriptor(path = "disposizioni-speciali-imball-adr-list-view.xml")
@LookupComponent("disposizioniSpecialiImballADRsDataGrid")
@DialogMode(width = "64em")
public class DisposizioniSpecialiImballADRListView extends StandardListView<DisposizioniSpecialiImballADR> {
    @ViewComponent
    private DataGrid<DisposizioniSpecialiImballADR> disposizioniSpecialiImballADRsDataGrid;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        disposizioniSpecialiImballADRsDataGrid.getColumnByKey("codice").setRenderer(new TextRenderer<>(DisposizioniSpecialiImballADR::getCodice));

    }
}