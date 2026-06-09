package it.ac.cargoflow.view.cliente;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "clientes", layout = MainView.class)
@ViewController(id = "Cliente.list")
@ViewDescriptor(path = "cliente-list-view.xml")
@LookupComponent("clientesDataGrid")
@DialogMode(width = "64em")
public class ClienteListView extends StandardListView<Cliente> {
}