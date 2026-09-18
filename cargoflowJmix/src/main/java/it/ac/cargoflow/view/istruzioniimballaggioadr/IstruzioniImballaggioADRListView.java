package it.ac.cargoflow.view.istruzioniimballaggioadr;

import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.IstruzioniImballaggioADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "istruzioni-imballaggio-adrs", layout = MainView.class)
@ViewController(id = "IstruzioniImballaggioADR.list")
@ViewDescriptor(path = "istruzioni-imballaggio-adr-list-view.xml")
@LookupComponent("istruzioniImballaggioADRsDataGrid")
@DialogMode(width = "64em")
public class IstruzioniImballaggioADRListView extends StandardListView<IstruzioniImballaggioADR> {
    @ViewComponent
    private DataGrid<IstruzioniImballaggioADR> istruzioniImballaggioADRsDataGrid;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        istruzioniImballaggioADRsDataGrid.getColumnByKey("codice").setRenderer(new TextRenderer<>(IstruzioniImballaggioADR::getCodice));

    }
}