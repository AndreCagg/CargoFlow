package it.ac.cargoflow.view.incarico;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.IconRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.Incarico;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "incaricoes", layout = MainView.class)
@ViewController(id = "Incarico.list")
@ViewDescriptor(path = "incarico-list-view.xml")
@LookupComponent("incaricoesDataGrid")
@DialogMode(width = "64em")
public class IncaricoListView extends StandardListView<Incarico> {
    @ViewComponent
    private DataGrid<Incarico> incaricoesDataGrid;
    @Autowired
    private UiComponents ui;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        incaricoesDataGrid.getColumnByKey("ritiro").setRenderer(new ComponentRenderer<>(obj -> {
            Icon icon = ui.create(Icon.class);

            if(obj.getRitiro()){
                icon.setIcon(VaadinIcon.CHECK);
            }else{
                icon.setIcon(VaadinIcon.CLOSE_SMALL);
            }

            return icon;
        }));

        incaricoesDataGrid.getColumnByKey("contrassegno").setRenderer(new ComponentRenderer<>(obj -> {
            Icon icon = ui.create(Icon.class);

            if(obj.getContrassegno()){
                icon.setIcon(VaadinIcon.CHECK);
            }else{
                icon.setIcon(VaadinIcon.CLOSE_SMALL);
            }

            return icon;
        }));

    }
}