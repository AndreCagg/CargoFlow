package it.ac.cargoflow.view.sede;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.Sede;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "sedes/:id", layout = MainView.class)
@ViewController(id = "Sede.detail")
@ViewDescriptor(path = "sede-detail-view.xml")
@EditedEntityContainer("sedeDc")
public class SedeDetailView extends StandardDetailView<Sede> {
}