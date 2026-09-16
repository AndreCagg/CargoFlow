package it.ac.cargoflow.view.incaricofasciaoraria;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.timepicker.TypedTimePicker;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.IncaricoFasciaOraria;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;

@Route(value = "incarico-fascia-orarias/:id", layout = MainView.class)
@ViewController(id = "IncaricoFasciaOraria.detail")
@ViewDescriptor(path = "incarico-fascia-oraria-detail-view.xml")
@EditedEntityContainer("incaricoFasciaOrariaDc")
public class IncaricoFasciaOrariaDetailView extends StandardDetailView<IncaricoFasciaOraria> {
    @ViewComponent
    private TimePicker dalleField;

    @ViewComponent
    private TimePicker alleField;

    @Autowired
    private ViewValidation validation;

    @ViewComponent
    private JmixCheckbox lun;

    @ViewComponent
    private JmixCheckbox mar;

    @ViewComponent
    private JmixCheckbox mer;

    @ViewComponent
    private JmixCheckbox gio;

    @ViewComponent
    private JmixCheckbox ven;

    @ViewComponent
    private JmixCheckbox sab;

    @ViewComponent
    private JmixCheckbox dom;
    @ViewComponent
    private JmixCheckbox sempre;

    private int ggCountWeek = 0;
    private int ggCountJobWeek = 0;
    @ViewComponent
    private JmixCheckbox lunven;
    @ViewComponent
    private JmixCheckbox solo_ritiroField;
    @ViewComponent
    private JmixCheckbox solo_consegnaField;

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        ValidationErrors errors = new ValidationErrors();
        if(this.dalleField.getValue()==null && this.alleField.getValue()==null){
            errors.add(Costants.FASCIA_ORARIA_NON_VALIDA);
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

    @Subscribe(id = "sempre", subject = "clickListener")
    public void onSempreClick(final ClickEvent<JmixCheckbox> event) {
        lunven.setValue(false);
        ggCountJobWeek = 0;
        boolean checked = event.getSource().getValue();
        checkDays(checked, false);

        ggCountWeek = checked ? 7 : 0;
    }

    @Subscribe(id = "lun", subject = "clickListener")
    public void onLunClick(final ClickEvent<JmixCheckbox> event) {
        updateDayState(event);
    }

    @Subscribe(id = "mar", subject = "clickListener")
    public void onMarClick(final ClickEvent event) {
        updateDayState(event);
    }

    @Subscribe(id = "mer", subject = "clickListener")
    public void onMerClick(final ClickEvent event) {
        updateDayState(event);
    }

    @Subscribe(id = "gio", subject = "clickListener")
    public void onGioClick(final ClickEvent event) {
        updateDayState(event);
    }

    @Subscribe(id = "ven", subject = "clickListener")
    public void onVenClick(final ClickEvent event) {
        updateDayState(event);
    }

    @Subscribe(id = "sab", subject = "clickListener")
    public void onSabClick(final ClickEvent event) {
        updateDayState(event);
    }

    @Subscribe(id = "dom", subject = "clickListener")
    public void onDomClick(final ClickEvent event) {
        updateDayState(event);
    }

    private void updateDayState(final ClickEvent<JmixCheckbox> event) {
        boolean checked = Boolean.TRUE.equals(event.getSource().getValue());
        boolean workDay = !List.of("Sabato", "Domenica").contains(event.getSource().getLabel());

        ggCountWeek = checked ? ggCountWeek + 1 : ggCountWeek - 1;
        if (workDay) {
            ggCountJobWeek = checked ? ggCountJobWeek + 1 : ggCountJobWeek - 1;
        }

        boolean sabChecked = Boolean.TRUE.equals(sab.getValue());
        boolean domChecked = Boolean.TRUE.equals(dom.getValue());

        if (ggCountWeek == 7) {
            sempre.setValue(true);
        } else if (!checked && ggCountWeek < 7) {
            sempre.setValue(false);
        }

        boolean isLunVenActive = (ggCountJobWeek == 5) && !sabChecked && !domChecked;
        lunven.setValue(isLunVenActive);
    }

    @Subscribe(id = "lunven", subject = "clickListener")
    public void onLunvenClick(final ClickEvent<JmixCheckbox> event) {
        sempre.setValue(false);
        ggCountWeek = 0;
        boolean checked = event.getSource().getValue();
        checkDays(checked, true);

        ggCountJobWeek = checked ? 5 : 0;
    }

    private void checkDays(boolean checked, boolean jobWeek){
        lun.setValue(checked);
        mar.setValue(checked);
        mer.setValue(checked);
        gio.setValue(checked);
        ven.setValue(checked);
        sab.setValue(!jobWeek && checked);
        dom.setValue(!jobWeek && checked);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        boolean sempreb = !List.of(lun.getValue(), mar.getValue(), mer.getValue(), gio.getValue(), ven.getValue(), sab.getValue(), dom.getValue()).stream().anyMatch(Boolean.FALSE::equals);
        sempre.setValue(sempreb);
        if(sempreb) return;

        lunven.setValue(!List.of(lun.getValue(), mar.getValue(), mer.getValue(), gio.getValue(), ven.getValue()).stream().anyMatch(Boolean.FALSE::equals));
    }

    @Subscribe(id = "solo_ritiroField", subject = "clickListener")
    public void onSolo_ritiroFieldClick(final ClickEvent<JmixCheckbox> event) {
        boolean b = event.getSource().getValue();

        if(b)
            solo_consegnaField.setValue(!b);
    }

    @Subscribe(id = "solo_consegnaField", subject = "clickListener")
    public void onSolo_consegnaFieldClick(final ClickEvent<JmixCheckbox> event) {
        boolean b = event.getSource().getValue();

        if(b)
            solo_ritiroField.setValue(!b);
    }
}