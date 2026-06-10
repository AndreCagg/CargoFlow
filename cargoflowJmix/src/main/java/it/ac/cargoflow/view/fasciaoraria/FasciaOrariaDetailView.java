package it.ac.cargoflow.view.fasciaoraria;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.timepicker.TypedTimePicker;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.FasciaOraria;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

@Route(value = "fascia-orarias/:id", layout = MainView.class)
@ViewController(id = "FasciaOraria.detail")
@ViewDescriptor(path = "fascia-oraria-detail-view.xml")
@EditedEntityContainer("fasciaOrariaDc")
public class FasciaOrariaDetailView extends StandardDetailView<FasciaOraria> {

    @ViewComponent
    private TimePicker dalleField;

    @ViewComponent
    private TimePicker alleField;

    @Autowired
    private ViewValidation validation;

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        ValidationErrors errors = new ValidationErrors();
        if(this.dalleField.getValue()!=null && this.alleField.getValue()!=null){
            errors.add(Costants.INTERVALLO_ORARIO_NON_VALIDO);
            this.setInvalidDalleAlle(true);
        }

        if(!errors.isEmpty()){
            this.validation.showValidationErrors(errors);
            event.preventSave();
        }
    }

    @Subscribe("dalleField")
    public void onDalleFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTimePicker<LocalTime>, LocalTime> event) {
        LocalTime dalle = event.getSource().getValue();
        LocalTime alle = this.alleField.getValue();

        if(dalle!=null && alle!=null && dalle.isAfter(alle)){
            this.setInvalidDalleAlle(true);
        }else{
            this.setInvalidDalleAlle(false);
        }
    }

    @Subscribe("alleField")
    public void onAlleFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTimePicker<LocalTime>, LocalTime> event) {
        LocalTime alle = event.getSource().getValue();
        LocalTime dalle = this.dalleField.getValue();

        if(dalle!=null && alle!=null && dalle.isAfter(alle)){
            this.setInvalidDalleAlle(true);
        }else{
            this.setInvalidDalleAlle(false);
        }
    }

    private void setInvalidDalleAlle(Boolean invalid){
        this.dalleField.setInvalid(invalid);
        this.alleField.setInvalid(invalid);

        if(invalid){
            this.dalleField.setErrorMessage(Costants.INTERVALLO_ORARIO_NON_VALIDO);
            this.alleField.setErrorMessage(Costants.INTERVALLO_ORARIO_NON_VALIDO);
        }
    }


}