package it.ac.cargoflow.view.trasportocolli;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.TrasportoColli;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "trasporto-collis/:id", layout = MainView.class)
@ViewController(id = "TrasportoColli.detail")
@ViewDescriptor(path = "trasporto-colli-detail-view.xml")
@EditedEntityContainer("trasportoColliDc")
public class TrasportoColliDetailView extends StandardDetailView<TrasportoColli> {
}