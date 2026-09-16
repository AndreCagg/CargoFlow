package it.ac.cargoflow.view.disposizionicisternaadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.DisposizioniCisternaADR;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "disposizioni-cisterna-adrs/:id", layout = MainView.class)
@ViewController(id = "DisposizioniCisternaADR.detail")
@ViewDescriptor(path = "disposizioni-cisterna-adr-detail-view.xml")
@EditedEntityContainer("disposizioniCisternaADRDc")
public class DisposizioniCisternaADRDetailView extends StandardDetailView<DisposizioniCisternaADR> {
}