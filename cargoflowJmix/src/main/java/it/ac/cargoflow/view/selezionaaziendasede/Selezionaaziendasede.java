package it.ac.cargoflow.view.selezionaaziendasede;


import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.session.SessionData;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.entity.Ruoli;
import it.ac.cargoflow.entity.Sede;
import it.ac.cargoflow.entity.User;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "SelezionaAziendaSede", layout = MainView.class)
@ViewController(id = "Selezionaaziendasede")
@ViewDescriptor(path = "SelezionaAziendaSede.xml")
public class Selezionaaziendasede extends StandardView {
    @ViewComponent
    private EntityComboBox azienda;

    @ViewComponent
    private EntityComboBox sede;

    @ViewComponent
    private CollectionLoader<Sede> sediDl;

    @Autowired
    private SessionData session;

    @Autowired
    private CurrentAuthentication auth;

    @Subscribe(id = "avanti", subject = "clickListener")
    public void onAvantiClick(final ClickEvent<JmixButton> event) {
        Azienda a = (Azienda) this.azienda.getValue();
        Sede s = (Sede) this.sede.getValue();

        if(a!=null){
            this.session.setAttribute(Costants.AZIENDA_KEY, a);
        }else{
            a = ((User) this.auth.getUser()).getAzienda();
        }

        if(s!=null){
            this.session.setAttribute(Costants.SEDE_KEY, s);
        }

        MainView mainView = (MainView) UI.getCurrent().getChildren()
                .filter(c -> c instanceof MainView)
                .findFirst()
                .orElse(null);

        if (mainView != null) {
            mainView.setAziendaSede(a, s);
        }

        this.navigateToMain();

    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User u = (User) this.auth.getUser();

        if(u.getRuolo().equals(Ruoli.ADMIN_SAAS)){
            this.azienda.setVisible(true);
        }

        if(List.of(Ruoli.ADMIN_SAAS, Ruoli.ADMIN_AZIENDA).contains(u.getRuolo())){
            this.sede.setVisible(true);

            if(u.getRuolo().equals(Ruoli.ADMIN_AZIENDA)){
                Azienda a = ((User) this.auth.getUser()).getAzienda();
                this.session.setAttribute(Costants.AZIENDA_KEY, a);
                this.loadSedi(u.getAzienda());
            }
        }
    }

    @Subscribe("salta")
    public void onSaltaClick(ClickEvent<Button> event) {
        this.navigateToMain();
    }

    private void navigateToMain(){
        UI.getCurrent().navigate(MainView.class);
    }


    @Subscribe("azienda")
    public void onAziendaComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Azienda>, Azienda> event) {
        Azienda a = (Azienda) this.azienda.getValue();
        this.loadSedi(a);
    }

    private void loadSedi(Azienda a){
        this.sede.setValue(null);

        if(a!=null){
            this.sediDl.setParameter("idAzienda", a.getId());
            this.sediDl.load();
        }
    }
}