package it.ac.cargoflow.view.esercizi;

import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Esercizi;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "esercizis", layout = MainView.class)
@ViewController(id = "Esercizi.list")
@ViewDescriptor(path = "esercizi-list-view.xml")
@LookupComponent("esercizisDataGrid")
@DialogMode(width = "64em")
public class EserciziListView extends StandardListView<Esercizi> {
    @ViewComponent
    private DataGrid<Esercizi> esercizisDataGrid;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        esercizisDataGrid.getColumnByKey("codice").setRenderer(new TextRenderer<>(obj -> "S"+obj.getCodice()));

    }
}