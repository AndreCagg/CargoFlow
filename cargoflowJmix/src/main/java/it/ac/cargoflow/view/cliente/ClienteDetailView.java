package it.ac.cargoflow.view.cliente;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.DataGridColumn;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.app.AziendaSedeContext;
import it.ac.cargoflow.entity.Cliente;
import it.ac.cargoflow.entity.FasciaOraria;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "clientes/:id", layout = MainView.class)
@ViewController(id = "Cliente.detail")
@ViewDescriptor(path = "cliente-detail-view.xml")
@EditedEntityContainer("clienteDc")
public class ClienteDetailView extends StandardDetailView<Cliente> {
    @Autowired
    private AziendaSedeContext asc;
    @ViewComponent
    private DataGrid<FasciaOraria> fasceOrarieDataGrid;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        getEditedEntity().setAzienda(asc.getAzienda());
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        DataGridColumn<FasciaOraria> fo = fasceOrarieDataGrid.getColumnByKey("giorni");
        fo.setRenderer(new TextRenderer<>(item -> {
            List<String> giorni = new ArrayList<>();
            if (Boolean.TRUE.equals(item.getLun())) giorni.add("Lun");
            if (Boolean.TRUE.equals(item.getMar())) giorni.add("Mar");
            if (Boolean.TRUE.equals(item.getMer())) giorni.add("Mer");
            if (Boolean.TRUE.equals(item.getGio())) giorni.add("Gio");
            if (Boolean.TRUE.equals(item.getVen())) giorni.add("Ven");
            if (Boolean.TRUE.equals(item.getSab())) giorni.add("Sab");
            if (Boolean.TRUE.equals(item.getDom())) giorni.add("Dom");

            return giorni.stream().collect(Collectors.joining(", "));
        }));

        DataGridColumn<FasciaOraria> co = fasceOrarieDataGrid.getColumnByKey("solo_consegna");
        DataGridColumn<FasciaOraria> ri = fasceOrarieDataGrid.getColumnByKey("solo_ritiro");

        co.setRenderer(new TextRenderer<>(item -> Boolean.TRUE.equals(item.getSolo_consegna()) ? "✓" : "✗"));
        ri.setRenderer(new TextRenderer<>(item -> Boolean.TRUE.equals(item.getSolo_ritiro()) ? "✓" : "✗"));

    }
}