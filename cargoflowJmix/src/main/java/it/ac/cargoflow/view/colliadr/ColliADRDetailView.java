package it.ac.cargoflow.view.colliadr;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.action.list.AddAction;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.ColliADR;
import it.ac.cargoflow.entity.Merce;
import it.ac.cargoflow.entity.TipoImballaggio;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route(value = "colli-adrs/:id", layout = MainView.class)
@ViewController(id = "ColliADR.detail")
@ViewDescriptor(path = "colli-adr-detail-view.xml")
@EditedEntityContainer("colliADRDc")
public class ColliADRDetailView extends StandardDetailView<ColliADR> {
    @ViewComponent
    private DataContext dc;
    @ViewComponent
    private CollectionContainer<Merce> mercesDc;
    private List<Merce> merci;
    private List<Merce> selezionata = new ArrayList<>();
    @ViewComponent
    private DataGrid<Merce> mercesDataGrid;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Notifications notifications;
    private TipoImballaggio ti;
    @ViewComponent
    private JmixSelect<TipoImballaggio> tipoImballaggioField;

    public void setMerce(List<Merce> merce){
        merci = merce;
    }

    public List<Merce> getSelezionata(){
        return selezionata;
    }

    public TipoImballaggio getTipoImballaggio(){
        return ti;
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if(mercesDc.getItems().isEmpty()){
            mercesDc.getMutableItems().addAll(merci);
        }


        mercesDataGrid.getColumnByKey("selezionato").setRenderer(new ComponentRenderer<>(obj -> {
            JmixCheckbox ch = uiComponents.create(JmixCheckbox.class);
            ch.setValue(false);
            ch.addValueChangeListener(checkev -> {
                if(checkev.getValue()){
                    selezionata.add(obj);
                }else{
                    selezionata.remove(obj);
                }
            });


            return ch;
        }));
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        if(selezionata.isEmpty()){
            notifications.create("E' necessario selezionare almeno un collo")
                    .withThemeVariant(NotificationVariant.LUMO_ERROR)
                    .show();
            event.preventSave();
            return;
        }

        ti = tipoImballaggioField.getValue();

    }


}