package it.ac.cargoflow.view.elementoadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.ElementoADR;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "elemento-adrs/:id", layout = MainView.class)
@ViewController(id = "ElementoADR.detail")
@ViewDescriptor(path = "elemento-adr-detail-view.xml")
@EditedEntityContainer("elementoADRDc")
public class ElementoADRDetailView extends StandardDetailView<ElementoADR> {
}