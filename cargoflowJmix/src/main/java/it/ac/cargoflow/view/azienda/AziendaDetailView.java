package it.ac.cargoflow.view.azienda;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "aziendas/:id", layout = MainView.class)
@ViewController(id = "Azienda.detail")
@ViewDescriptor(path = "azienda-detail-view.xml")
@EditedEntityContainer("aziendaDc")
public class AziendaDetailView extends StandardDetailView<Azienda> {
}