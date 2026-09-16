package it.ac.cargoflow.view.imballaggiocomune;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.ImballaggioComune;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "imballaggio-comunes", layout = MainView.class)
@ViewController(id = "ImballaggioComune.list")
@ViewDescriptor(path = "imballaggio-comune-list-view.xml")
@LookupComponent("imballaggioComunesDataGrid")
@DialogMode(width = "64em")
public class ImballaggioComuneListView extends StandardListView<ImballaggioComune> {
}