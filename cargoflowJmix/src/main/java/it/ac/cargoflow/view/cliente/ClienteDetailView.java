package it.ac.cargoflow.view.cliente;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.app.AziendaSedeContext;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "clientes/:id", layout = MainView.class)
@ViewController(id = "Cliente.detail")
@ViewDescriptor(path = "cliente-detail-view.xml")
@EditedEntityContainer("clienteDc")
public class ClienteDetailView extends StandardDetailView<Cliente> {
    @Autowired
    private AziendaSedeContext asc;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        getEditedEntity().setAzienda(asc.getAzienda());
    }
}