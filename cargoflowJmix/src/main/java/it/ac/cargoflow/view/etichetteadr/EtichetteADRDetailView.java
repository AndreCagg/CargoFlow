package it.ac.cargoflow.view.etichetteadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.EtichetteADR;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "etichette-adrs/:id", layout = MainView.class)
@ViewController(id = "EtichetteADR.detail")
@ViewDescriptor(path = "etichette-adr-detail-view.xml")
@EditedEntityContainer("etichetteADRDc")
public class EtichetteADRDetailView extends StandardDetailView<EtichetteADR> {
}