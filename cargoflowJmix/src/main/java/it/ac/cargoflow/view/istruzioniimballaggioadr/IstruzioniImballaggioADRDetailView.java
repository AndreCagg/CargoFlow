package it.ac.cargoflow.view.istruzioniimballaggioadr;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.IstruzioniImballaggioADR;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "istruzioni-imballaggio-adrs/:id", layout = MainView.class)
@ViewController(id = "IstruzioniImballaggioADR.detail")
@ViewDescriptor(path = "istruzioni-imballaggio-adr-detail-view.xml")
@EditedEntityContainer("istruzioniImballaggioADRDc")
public class IstruzioniImballaggioADRDetailView extends StandardDetailView<IstruzioniImballaggioADR> {
}