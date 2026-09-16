package it.ac.cargoflow.view.trasportocolli;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.TrasportoColli;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "trasporto-collis", layout = MainView.class)
@ViewController(id = "TrasportoColli.list")
@ViewDescriptor(path = "trasporto-colli-list-view.xml")
@LookupComponent("trasportoCollisDataGrid")
@DialogMode(width = "64em")
public class TrasportoColliListView extends StandardListView<TrasportoColli> {
}