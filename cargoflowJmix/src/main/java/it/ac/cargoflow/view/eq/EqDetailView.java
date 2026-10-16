package it.ac.cargoflow.view.eq;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.Eq;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "eqs/:id", layout = MainView.class)
@ViewController(id = "Eq.detail")
@ViewDescriptor(path = "eq-detail-view.xml")
@EditedEntityContainer("eqDc")
public class EqDetailView extends StandardDetailView<Eq> {
}