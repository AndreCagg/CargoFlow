package it.ac.cargoflow.view.elementoadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.ElementoADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "elemento-adrs", layout = MainView.class)
@ViewController(id = "ElementoADR.list")
@ViewDescriptor(path = "elemento-adr-list-view.xml")
@LookupComponent("elementoADRsDataGrid")
@DialogMode(width = "64em")
public class ElementoADRListView extends StandardListView<ElementoADR> {
}