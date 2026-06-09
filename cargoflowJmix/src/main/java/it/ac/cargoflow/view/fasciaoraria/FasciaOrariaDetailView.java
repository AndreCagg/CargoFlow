package it.ac.cargoflow.view.fasciaoraria;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.FasciaOraria;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "fascia-orarias/:id", layout = MainView.class)
@ViewController(id = "FasciaOraria.detail")
@ViewDescriptor(path = "fascia-oraria-detail-view.xml")
@EditedEntityContainer("fasciaOrariaDc")
public class FasciaOrariaDetailView extends StandardDetailView<FasciaOraria> {
}