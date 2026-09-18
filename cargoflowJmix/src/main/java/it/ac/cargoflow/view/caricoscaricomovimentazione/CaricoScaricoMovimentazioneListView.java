package it.ac.cargoflow.view.caricoscaricomovimentazione;

import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.CaricoScaricoMovimentazione;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "carico-scarico-movimentaziones", layout = MainView.class)
@ViewController(id = "CaricoScaricoMovimentazione.list")
@ViewDescriptor(path = "carico-scarico-movimentazione-list-view.xml")
@LookupComponent("caricoScaricoMovimentazionesDataGrid")
@DialogMode(width = "64em")
public class CaricoScaricoMovimentazioneListView extends StandardListView<CaricoScaricoMovimentazione> {
    @ViewComponent
    private DataGrid<CaricoScaricoMovimentazione> caricoScaricoMovimentazionesDataGrid;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        caricoScaricoMovimentazionesDataGrid.getColumnByKey("sigla").setRenderer(new TextRenderer<>(obj -> {
            return "CV"+obj.getSigla();
        }));

    }
}