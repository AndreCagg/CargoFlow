package it.ac.cargoflow.view.esercizi;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.Esercizi;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "esercizis/:id", layout = MainView.class)
@ViewController(id = "Esercizi.detail")
@ViewDescriptor(path = "esercizi-detail-view.xml")
@EditedEntityContainer("eserciziDc")
public class EserciziDetailView extends StandardDetailView<Esercizi> {
}