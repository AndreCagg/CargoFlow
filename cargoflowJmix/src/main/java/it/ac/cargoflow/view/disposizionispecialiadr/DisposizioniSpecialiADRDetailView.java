package it.ac.cargoflow.view.disposizionispecialiadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.DisposizioniSpecialiADR;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "disposizioni-speciali-adrs/:id", layout = MainView.class)
@ViewController(id = "DisposizioniSpecialiADR.detail")
@ViewDescriptor(path = "disposizioni-speciali-adr-detail-view.xml")
@EditedEntityContainer("disposizioniSpecialiADRDc")
public class DisposizioniSpecialiADRDetailView extends StandardDetailView<DisposizioniSpecialiADR> {
}