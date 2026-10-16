package it.ac.cargoflow.view.elementoadr;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.*;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.*;

@Route(value = "elemento-adrs/:id", layout = MainView.class)
@ViewController(id = "ElementoADR.detail")
@ViewDescriptor(path = "elemento-adr-detail-view.xml")
@EditedEntityContainer("elementoADRDc")
public class ElementoADRDetailView extends StandardDetailView<ElementoADR> {
    @ViewComponent
    private DataGrid<CaricoScaricoMovimentazione> caricoScaricoMovimentazioneDataGrid;
    @ViewComponent
    private DataGrid<Esercizi> eserciziDataGrid;
    @ViewComponent
    private TypedTextField<Object> limiteGalleriaField;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private JmixSelect<TipoCisterna> codiceCisternaField;
    @ViewComponent
    private EntityComboBox<DisposizioniCisternaADR> disposizioniCisternaField;
    @ViewComponent
    private JmixCheckbox rinfusa;
    @ViewComponent
    private JmixCheckbox cisterna;
    @ViewComponent
    private JmixSelect<GruppoImballaggio> gruppoImballaggioField;
    @ViewComponent
    private EntityComboBox<LQ> quantitaLimitateField;
    @ViewComponent
    private TypedTextField<Integer> quantitaEsentiField;
    @ViewComponent
    private JmixCheckbox colli;
    @ViewComponent
    private VerticalLayout etichette;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        caricoScaricoMovimentazioneDataGrid.getColumnByKey("sigla").setRenderer(new TextRenderer<>(obj -> "CV"+obj.getSigla()));
        eserciziDataGrid.getColumnByKey("codice").setRenderer(new TextRenderer<>(obj -> "S"+obj.getCodice()));
        Character sup = getEditedEntity().getCodRestrizioneA();

        if(sup!=null){
            limiteGalleriaField.setValue(sup+getEditedEntity().getLimiteGalleria().toString()+getEditedEntity().getCodRestrizioneB());
        }
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        boolean colliB = colli.getValue(), rinfusaB = rinfusa.getValue(), cisternaB = cisterna.getValue();

        String reg = limiteGalleriaField.getValue();
        boolean invalid = limiteGalleriaField.isInvalid();

        if(limiteGalleriaField.getValue()!=null && !invalid){
            char sup = 'A', inf = 'A';
            String peso = "";
            int limiteInf = reg.length()-1;
            sup = reg.charAt(0);
            inf = reg.charAt(limiteInf);
            peso = reg.substring(1, limiteInf);

            getEditedEntity().setCodRestrizioneA(sup);
            getEditedEntity().setCodRestrizioneB(inf);
            getEditedEntity().setLimiteGalleria(Integer.parseInt(peso));
        }else{
            getEditedEntity().setCodRestrizioneA(null);
            getEditedEntity().setCodRestrizioneB(null);
            getEditedEntity().setLimiteGalleria(null);
        }

        if(cisternaB && codiceCisternaField.getValue()==null && disposizioniCisternaField.getValue()!=null){
            notifications.create("Non è possibile salvare la materia ADR in quanto è specificata la disposizione della cisterna ma non il codice della cisterna")
                    .withType(Notifications.Type.ERROR)
                    .show();

            event.preventSave();
            return;
        }
    }

    @Subscribe("colli")
    public void onColliValueChange(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        if (Boolean.TRUE.equals(event.getValue())) {
            resetAndHideAll(TipoADR.COLLI);

            gruppoImballaggioField.setVisible(true);
            quantitaLimitateField.setVisible(true);
            quantitaEsentiField.setVisible(true);

            etichette.setVisible(true);
        }else if(!colli.getValue() && !rinfusa.getValue() && !cisterna.getValue()){
            resetAndHideAll(null);
        }
    }

    @Subscribe("rinfusa")
    public void onRinfusaValueChange(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        if (Boolean.TRUE.equals(event.getValue())) {
            resetAndHideAll(TipoADR.RINFUSA);
            rinfusa.setValue(true);

        }else if(!colli.getValue() && !rinfusa.getValue() && !cisterna.getValue()){
            resetAndHideAll(null);
        }
    }

    @Subscribe("cisterna")
    public void onCisternaValueChange(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        if (Boolean.TRUE.equals(event.getValue())) {
            resetAndHideAll(TipoADR.CISTERNA);
            cisterna.setValue(true);

            codiceCisternaField.setVisible(true);
            disposizioniCisternaField.setVisible(true);
        }else if(!colli.getValue() && !rinfusa.getValue() && !cisterna.getValue()){
            resetAndHideAll(null);
        }
    }

    private void resetAndHideAll(@Nullable TipoADR tipo) {
        if(tipo == null || !tipo.equals(TipoADR.COLLI))
            colli.setValue(false);

        if(tipo == null || !tipo.equals(TipoADR.RINFUSA))
            rinfusa.setValue(false);

        if(tipo == null || !tipo.equals(TipoADR.CISTERNA))
            cisterna.setValue(false);

        gruppoImballaggioField.clear();
        quantitaLimitateField.clear();
        quantitaEsentiField.clear();
        codiceCisternaField.clear();
        disposizioniCisternaField.clear();

        if (getEditedEntity().getEtichette() != null) {
            getEditedEntity().getEtichette().clear();
        }

        gruppoImballaggioField.setVisible(false);
        quantitaLimitateField.setVisible(false);
        quantitaEsentiField.setVisible(false);
        codiceCisternaField.setVisible(false);
        disposizioniCisternaField.setVisible(false);

        etichette.setVisible(false);
    }
}