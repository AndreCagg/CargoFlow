package it.ac.cargoflow.view.veicolitrasporto;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.VeicoliTrasporto;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "veicoli-trasportoes", layout = MainView.class)
@ViewController(id = "VeicoliTrasporto.list")
@ViewDescriptor(path = "veicoli-trasporto-list-view.xml")
@LookupComponent("veicoliTrasportoesDataGrid")
@DialogMode(width = "64em")
public class VeicoliTrasportoListView extends StandardListView<VeicoliTrasporto> {
}