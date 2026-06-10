package it.ac.cargoflow.view.incaricofasciaoraria;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.IncaricoFasciaOraria;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "incarico-fascia-orarias/:id", layout = MainView.class)
@ViewController(id = "IncaricoFasciaOraria.detail")
@ViewDescriptor(path = "incarico-fascia-oraria-detail-view.xml")
@EditedEntityContainer("incaricoFasciaOrariaDc")
public class IncaricoFasciaOrariaDetailView extends StandardDetailView<IncaricoFasciaOraria> {
}