package it.ac.cargoflow.view.incaricosedemittdest;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.IncaricoSedeMittDest;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "incarico-sede-mitt-dests/:id", layout = MainView.class)
@ViewController(id = "IncaricoSedeMittDest.detail")
@ViewDescriptor(path = "incarico-sede-mitt-dest-detail-view.xml")
@EditedEntityContainer("incaricoSedeMittDestDc")
public class IncaricoSedeMittDestDetailView extends StandardDetailView<IncaricoSedeMittDest> {
}