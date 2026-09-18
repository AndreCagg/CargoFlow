package it.ac.cargoflow.view.lq;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.LQ;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "l-qs", layout = MainView.class)
@ViewController(id = "LQ.list")
@ViewDescriptor(path = "lq-list-view.xml")
@LookupComponent("lQsDataGrid")
@DialogMode(width = "64em")
public class LQListView extends StandardListView<LQ> {
}