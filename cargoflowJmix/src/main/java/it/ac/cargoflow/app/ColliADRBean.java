package it.ac.cargoflow.app;

import io.jmix.core.DataManager;
import it.ac.cargoflow.entity.ColliADR;
import it.ac.cargoflow.entity.Incarico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColliADRBean {
    @Autowired
    private DataManager dm;

    public boolean ciSonoColli(Incarico i){
        return dm.load(ColliADR.class)
                .query("select e from ColliADR e join e.colli m where m.incarico = :i")
                .parameter("i", i)
                .optional().isPresent();
    }
}