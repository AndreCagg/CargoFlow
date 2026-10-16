package it.ac.cargoflow.app;

import io.jmix.core.DataManager;
import it.ac.cargoflow.entity.Incarico;
import it.ac.cargoflow.entity.Movimenti;
import it.ac.cargoflow.entity.StatoMovimenti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncaricoBean {
    @Autowired
    private DataManager dm;

    public StatoMovimenti ultimoMovimento(Incarico i){
        return dm.load(Movimenti.class)
                .query("select e from Incarico a join a.movimenti e where a = :i order by e.data desc")
                .parameter("i", i)
                .maxResults(1)
                .optional().orElse(null).getDescrizione();
    }
}