package it.ac.cargoflow.view.sede;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Sede;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "sedes", layout = MainView.class)
@ViewController(id = "Sede.list")
@ViewDescriptor(path = "sede-list-view.xml")
@LookupComponent("sedesDataGrid")
@DialogMode(width = "64em")
public class SedeListView extends StandardListView<Sede> {
}