package it.ac.cargoflow.view.trasportorinfusa;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.TrasportoRinfusa;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "trasporto-rinfusas/:id", layout = MainView.class)
@ViewController(id = "TrasportoRinfusa.detail")
@ViewDescriptor(path = "trasporto-rinfusa-detail-view.xml")
@EditedEntityContainer("trasportoRinfusaDc")
public class TrasportoRinfusaDetailView extends StandardDetailView<TrasportoRinfusa> {
}