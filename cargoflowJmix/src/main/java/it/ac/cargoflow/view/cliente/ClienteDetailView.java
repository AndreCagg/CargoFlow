package it.ac.cargoflow.view.cliente;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "clientes/:id", layout = MainView.class)
@ViewController(id = "Cliente.detail")
@ViewDescriptor(path = "cliente-detail-view.xml")
@EditedEntityContainer("clienteDc")
public class ClienteDetailView extends StandardDetailView<Cliente> {
}