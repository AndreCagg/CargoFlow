package it.ac.cargoflow.view.imballaggiocomune;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.ImballaggioComune;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "imballaggio-comunes/:id", layout = MainView.class)
@ViewController(id = "ImballaggioComune.detail")
@ViewDescriptor(path = "imballaggio-comune-detail-view.xml")
@EditedEntityContainer("imballaggioComuneDc")
public class ImballaggioComuneDetailView extends StandardDetailView<ImballaggioComune> {
}