package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.entity.Incarico;
import it.ac.cargoflow.view.cliente.ClienteDetailView;
import it.ac.cargoflow.view.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "incaricoes/:id", layout = MainView.class)
@ViewController(id = "Incarico.detail")
@ViewDescriptor(path = "incarico-detail-view.xml")
@EditedEntityContainer("incaricoDc")
public class IncaricoDetailView extends StandardDetailView<Incarico> {
    @Autowired
    private DialogWindows dialogWindows;

    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private CollectionLoader<Cliente> clientiDl;

    @ViewComponent
    private EntityComboBox<Cliente> mittenteField;

    @Subscribe("mittenteField")
    public void onMittenteFieldCustomValueSet(
            ComboBoxBase.CustomValueSetEvent<?> event) {

        String ragioneSociale = event.getDetail();

        Cliente cliente = dataManager.create(Cliente.class);
        cliente.setRagione_sociale(ragioneSociale);

        DialogWindow<ClienteDetailView> dialog =
                dialogWindows.detail(this, Cliente.class)
                        .withViewClass(ClienteDetailView.class)
                        .editEntity(cliente)
                        .build();

        dialog.addAfterCloseListener(closeEvent -> {
            if (closeEvent.closedWith(StandardOutcome.SAVE)) {

                Cliente salvato = dialog.getView().getEditedEntity();

                clientiDl.load();

                mittenteField.setValue(salvato);
            } else {
                mittenteField.clear();
            }
        });

        dialog.open();
    }

    @Subscribe("destinatarioField")
    public void onDestinatarioFieldCustomValueSet(
            ComboBoxBase.CustomValueSetEvent<?> event) {

        String ragioneSociale = event.getDetail();

        Cliente cliente = dataManager.create(Cliente.class);
        cliente.setRagione_sociale(ragioneSociale);

        DialogWindow<ClienteDetailView> dialog =
                dialogWindows.detail(this, Cliente.class)
                        .withViewClass(ClienteDetailView.class)
                        .editEntity(cliente)
                        .build();

        dialog.addAfterCloseListener(closeEvent -> {
            if (closeEvent.closedWith(StandardOutcome.SAVE)) {

                Cliente salvato = dialog.getView().getEditedEntity();

                clientiDl.load();

                mittenteField.setValue(salvato);
            } else {
                mittenteField.clear();
            }
        });

        dialog.open();
    }


}