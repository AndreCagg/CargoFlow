package it.ac.cargoflow.app;

import com.vaadin.flow.component.UI;
import io.jmix.core.session.SessionData;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.entity.Ruoli;
import it.ac.cargoflow.entity.Sede;
import it.ac.cargoflow.entity.User;
import it.ac.cargoflow.view.main.MainView;
import it.ac.cargoflow.view.selezionaaziendasede.Selezionaaziendasede;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginBean {

    @Autowired
    private ObjectProvider<SessionData> sessionDataProvider;

    private Logger log = LoggerFactory.getLogger(LoginBean.class);

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {

        Authentication authentication = event.getAuthentication();

        if (authentication == null) {
            return;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User user)) {
            return;
        }

        UI ui = UI.getCurrent();

        if (ui == null) {
            return;
        }

        if (List.of(Ruoli.ADMIN_SAAS, Ruoli.ADMIN_AZIENDA).contains(user.getRuolo())) {
            Azienda a = user.getAzienda();
            Boolean isAdminAzienda = user.getRuolo().equals(Ruoli.ADMIN_AZIENDA);
            if(isAdminAzienda && (a == null || (a!=null && a.getSede().size()>1))) {
                ui.navigate(Selezionaaziendasede.class);
            }else if(isAdminAzienda){
                SessionData session = sessionDataProvider.getObject();
                session.setAttribute(Costants.AZIENDA_KEY, user.getAzienda());

                List<Sede> sedi = user.getAzienda().getSede();
                session.setAttribute(Costants.SEDE_KEY, (sedi !=null && sedi.size()>0) ? sedi.getFirst() : "");
                ui.navigate(MainView.class);
            }else{
                ui.navigate(Selezionaaziendasede.class);
            }
        } else {
            ui.navigate(MainView.class);
        }
    }
}