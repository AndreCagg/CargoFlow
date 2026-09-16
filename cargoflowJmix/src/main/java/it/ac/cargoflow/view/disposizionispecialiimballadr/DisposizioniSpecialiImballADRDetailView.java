package it.ac.cargoflow.view.disposizionispecialiimballadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.DisposizioniSpecialiImballADR;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "disposizioni-speciali-imball-adrs/:id", layout = MainView.class)
@ViewController(id = "DisposizioniSpecialiImballADR.detail")
@ViewDescriptor(path = "disposizioni-speciali-imball-adr-detail-view.xml")
@EditedEntityContainer("disposizioniSpecialiImballADRDc")
public class DisposizioniSpecialiImballADRDetailView extends StandardDetailView<DisposizioniSpecialiImballADR> {
}