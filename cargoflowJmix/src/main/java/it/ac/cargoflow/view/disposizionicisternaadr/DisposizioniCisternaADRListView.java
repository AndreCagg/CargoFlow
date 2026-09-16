package it.ac.cargoflow.view.disposizionicisternaadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.DisposizioniCisternaADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "disposizioni-cisterna-adrs", layout = MainView.class)
@ViewController(id = "DisposizioniCisternaADR.list")
@ViewDescriptor(path = "disposizioni-cisterna-adr-list-view.xml")
@LookupComponent("disposizioniCisternaADRsDataGrid")
@DialogMode(width = "64em")
public class DisposizioniCisternaADRListView extends StandardListView<DisposizioniCisternaADR> {
}