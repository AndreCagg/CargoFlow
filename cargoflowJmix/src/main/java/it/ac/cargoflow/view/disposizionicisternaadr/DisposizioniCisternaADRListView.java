package it.ac.cargoflow.view.disposizionicisternaadr;

import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.DisposizioniCisternaADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "disposizioni-cisterna-adrs", layout = MainView.class)
@ViewController(id = "DisposizioniCisternaADR.list")
@ViewDescriptor(path = "disposizioni-cisterna-adr-list-view.xml")
@LookupComponent("disposizioniCisternaADRsDataGrid")
@DialogMode(width = "64em")
public class DisposizioniCisternaADRListView extends StandardListView<DisposizioniCisternaADR> {
    @ViewComponent
    private DataGrid<DisposizioniCisternaADR> disposizioniCisternaADRsDataGrid;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        disposizioniCisternaADRsDataGrid.getColumnByKey("codice").setRenderer(new TextRenderer<>(obj -> {
            return obj.getTipo()+obj.getNum().toString();
        }));

    }
}