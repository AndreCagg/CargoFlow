package it.ac.cargoflow.view.merce;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Merce;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "merces", layout = MainView.class)
@ViewController(id = "Merce.list")
@ViewDescriptor(path = "merce-list-view.xml")
@LookupComponent("mercesDataGrid")
@DialogMode(width = "64em")
public class MerceListView extends StandardListView<Merce> {
}