package it.ac.cargoflow.view.fasciaoraria;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.FasciaOraria;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "fascia-orarias", layout = MainView.class)
@ViewController(id = "FasciaOraria.list")
@ViewDescriptor(path = "fascia-oraria-list-view.xml")
@LookupComponent("fasciaOrariasDataGrid")
@DialogMode(width = "64em")
public class FasciaOrariaListView extends StandardListView<FasciaOraria> {
}