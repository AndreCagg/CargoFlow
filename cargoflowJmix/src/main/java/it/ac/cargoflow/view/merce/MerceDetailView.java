package it.ac.cargoflow.view.merce;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.component.textfield.JmixNumberField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "merces/:id", layout = MainView.class)
@ViewController(id = "Merce.detail")
@ViewDescriptor(path = "merce-detail-view.xml")
@EditedEntityContainer("merceDc")
public class MerceDetailView extends StandardDetailView<Merce> {
    @ViewComponent
    private TypedTextField<String> id_epalField;
    @ViewComponent
    private JmixCheckbox epalField;
    @ViewComponent
    private JmixCheckbox adr;
    @ViewComponent
    private EntityComboBox<ElementoADR> elementoAdrField;
    @ViewComponent
    private JmixSelect<TipoMerce> merce_tipoField;
    @ViewComponent
    private VerticalLayout adrBox;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private JmixIntegerField confezioniIntAdr;
    @ViewComponent
    private JmixNumberField numConf;
    @ViewComponent
    private EntityComboBox<Imballaggio> imballaggioField;

    @Subscribe("epalField")
    public void onEpalFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        boolean b = event.getValue();

        id_epalField.setVisible(b);

        if(!b){
            id_epalField.setValue(null);
        }
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        id_epalField.setVisible(epalField.getValue());

        boolean adrPresente = elementoAdrField.getValue()!=null;
        adrBox.setVisible(adrPresente);
        adr.setValue(adrPresente);

        TipoMerce tm = merce_tipoField.getValue();

        if(tm!=null) {
            boolean pallet = tm.equals(TipoMerce.PALLET);
            epalField.setVisible(pallet);
        }else{
            epalField.setVisible(false);
        }
    }

    @Subscribe("adr")
    public void onAdrComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        boolean b = event.getValue();

        adrBox.setVisible(b);

        if(!b){
            elementoAdrField.setValue(null);
            confezioniIntAdr.setValue(0);
            numConf.setValue(0D);
            imballaggioField.setValue(null);
        }
    }

    @Subscribe("merce_tipoField")
    public void onMerce_tipoFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<TipoMerce>, TipoMerce> event) {
        boolean pallet = event.getValue().equals(TipoMerce.PALLET);
        epalField.setValue(false);
        epalField.setVisible(pallet);
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        if(!epalOk()){
            notifications.create("Non è possibile salvare la merce in quanto è indicato epal ma non è inserito il relativo ID")
                    .withType(Notifications.Type.ERROR)
                    .show();

            event.preventSave();
            return;
        }

        if(!adrOk()){
            notifications.create("Non è possibile salvare la merce in quanto è indicato ADR ma non è inserito il numero UN oppure manca la quantità")
                    .withType(Notifications.Type.ERROR)
                    .show();

            event.preventSave();
            return;
        }
    }

    @Subscribe("elementoAdrField")
    public void onElementoAdrFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<ElementoADR>, ElementoADR> event) {
        ElementoADR e = event.getValue();
        UM um = e.getUm();

        if(e!=null) {
            numConf.setPrefixComponent(new Span(um.toString()));
        }
    }

    private boolean epalOk(){
        return !(epalField.getValue() && id_epalField.getValue().isEmpty());
    }

    private boolean adrOk(){
        return !(adr.getValue() && (elementoAdrField.getValue()==null || confezioniIntAdr.isInvalid() || confezioniIntAdr.getValue()==null || confezioniIntAdr.getValue()==0 || numConf.getValue() == 0));
    }
}