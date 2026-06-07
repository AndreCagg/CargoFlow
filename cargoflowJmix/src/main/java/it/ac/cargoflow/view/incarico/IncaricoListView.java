package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Incarico;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "incaricoes", layout = MainView.class)
@ViewController(id = "Incarico.list")
@ViewDescriptor(path = "incarico-list-view.xml")
@LookupComponent("incaricoesDataGrid")
@DialogMode(width = "64em")
public class IncaricoListView extends StandardListView<Incarico> {
}