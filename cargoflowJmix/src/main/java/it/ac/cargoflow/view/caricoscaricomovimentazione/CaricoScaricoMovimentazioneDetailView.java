package it.ac.cargoflow.view.caricoscaricomovimentazione;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.CaricoScaricoMovimentazione;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "carico-scarico-movimentaziones/:id", layout = MainView.class)
@ViewController(id = "CaricoScaricoMovimentazione.detail")
@ViewDescriptor(path = "carico-scarico-movimentazione-detail-view.xml")
@EditedEntityContainer("caricoScaricoMovimentazioneDc")
public class CaricoScaricoMovimentazioneDetailView extends StandardDetailView<CaricoScaricoMovimentazione> {
}