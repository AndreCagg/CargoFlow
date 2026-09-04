package it.ac.cargoflow.app;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.entity.Sede;
import org.springframework.stereotype.Component;

@Component
@VaadinSessionScope
public class AziendaSedeContext {
    private Azienda a;
    private Sede s;

    public void setAziendaSedeCtx(Azienda a, Sede s){
        this.a = a;
        this.s = s;
    }

    public Azienda getAzienda(){
        return a;
    }

    public Sede getSede(){
        return s;
    }
}