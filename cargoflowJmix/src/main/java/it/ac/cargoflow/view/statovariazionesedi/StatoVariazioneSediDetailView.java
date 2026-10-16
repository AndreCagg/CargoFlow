package it.ac.cargoflow.view.statovariazionesedi;

import com.vaadin.flow.router.Route;
import io.jmix.core.EntityStates;
import io.jmix.flowui.component.datetimepicker.TypedDateTimePicker;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.StatoVariazioneSedi;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Route(value = "stato-variazione-sedis/:id", layout = MainView.class)
@ViewController(id = "StatoVariazioneSedi.detail")
@ViewDescriptor(path = "stato-variazione-sedi-detail-view.xml")
@EditedEntityContainer("statoVariazioneSediDc")
public class StatoVariazioneSediDetailView extends StandardDetailView<StatoVariazioneSedi> {
    @Autowired
    private EntityStates es;
    @ViewComponent
    private TypedDateTimePicker<LocalDateTime> dataStatoField;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        boolean isnew = es.isNew(getEditedEntity());
        dataStatoField.setReadOnly(isnew);

        if(isnew){
            dataStatoField.setValue(LocalDateTime.now());
        }
    }
}