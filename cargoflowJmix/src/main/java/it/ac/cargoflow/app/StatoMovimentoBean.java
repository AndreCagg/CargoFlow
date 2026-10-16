package it.ac.cargoflow.app;

import io.jmix.core.DataManager;
import it.ac.cargoflow.entity.StatoMovimenti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatoMovimentoBean {
    @Autowired
    private DataManager dc;

    public String getColore(StatoMovimenti sm){
        return switch (sm){
            case DISPONIBILE_MAGAZZINO -> "success";
            case NON_VALIDO -> "error";
            default -> "primary";
        };
    }
}