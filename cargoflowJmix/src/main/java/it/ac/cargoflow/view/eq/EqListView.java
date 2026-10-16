package it.ac.cargoflow.view.eq;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Eq;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "eqs", layout = MainView.class)
@ViewController(id = "Eq.list")
@ViewDescriptor(path = "eq-list-view.xml")
@LookupComponent("eqsDataGrid")
@DialogMode(width = "64em")
public class EqListView extends StandardListView<Eq> {
}