package it.ac.cargoflow.view.etichetteadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.EtichetteADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "etichette-adrs", layout = MainView.class)
@ViewController(id = "EtichetteADR.list")
@ViewDescriptor(path = "etichette-adr-list-view.xml")
@LookupComponent("etichetteADRsDataGrid")
@DialogMode(width = "64em")
public class EtichetteADRListView extends StandardListView<EtichetteADR> {
}