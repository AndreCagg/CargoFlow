package it.ac.cargoflow.view.merce;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.ElementoADR;
import it.ac.cargoflow.entity.Merce;
import it.ac.cargoflow.entity.TipoMerce;
import it.ac.cargoflow.view.main.MainView;

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

    @Subscribe("epalField")
    public void onEpalFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        id_epalField.setVisible(event.getValue());
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        id_epalField.setVisible(epalField.getValue());
        elementoAdrField.setVisible(adr.getValue());

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
        elementoAdrField.setVisible(event.getValue());
    }

    @Subscribe("merce_tipoField")
    public void onMerce_tipoFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<TipoMerce>, TipoMerce> event) {
        boolean pallet = event.getValue().equals(TipoMerce.PALLET);
        epalField.setValue(false);
        epalField.setVisible(pallet);
    }
}