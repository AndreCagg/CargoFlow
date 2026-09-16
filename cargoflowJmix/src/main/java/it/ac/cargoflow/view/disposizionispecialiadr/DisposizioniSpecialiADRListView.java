package it.ac.cargoflow.view.disposizionispecialiadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.DisposizioniSpecialiADR;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "disposizioni-speciali-adrs", layout = MainView.class)
@ViewController(id = "DisposizioniSpecialiADR.list")
@ViewDescriptor(path = "disposizioni-speciali-adr-list-view.xml")
@LookupComponent("disposizioniSpecialiADRsDataGrid")
@DialogMode(width = "64em")
public class DisposizioniSpecialiADRListView extends StandardListView<DisposizioniSpecialiADR> {
}