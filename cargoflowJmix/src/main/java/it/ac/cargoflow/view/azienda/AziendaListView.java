package it.ac.cargoflow.view.azienda;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "aziendas", layout = MainView.class)
@ViewController(id = "Azienda.list")
@ViewDescriptor(path = "azienda-list-view.xml")
@LookupComponent("aziendasDataGrid")
@DialogMode(width = "64em")
public class AziendaListView extends StandardListView<Azienda> {
}