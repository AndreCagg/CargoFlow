package it.ac.cargoflow.view.trasportorinfusa;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.TrasportoRinfusa;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "trasporto-rinfusas", layout = MainView.class)
@ViewController(id = "TrasportoRinfusa.list")
@ViewDescriptor(path = "trasporto-rinfusa-list-view.xml")
@LookupComponent("trasportoRinfusasDataGrid")
@DialogMode(width = "64em")
public class TrasportoRinfusaListView extends StandardListView<TrasportoRinfusa> {
}