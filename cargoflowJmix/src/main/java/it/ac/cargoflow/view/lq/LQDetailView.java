package it.ac.cargoflow.view.lq;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.LQ;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "l-qs/:id", layout = MainView.class)
@ViewController(id = "LQ.detail")
@ViewDescriptor(path = "lq-detail-view.xml")
@EditedEntityContainer("lQDc")
public class LQDetailView extends StandardDetailView<LQ> {
}