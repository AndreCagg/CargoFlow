package it.ac.cargoflow.view.incaricosedemittdest;

import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.IncaricoSedeMittDest;
import it.ac.cargoflow.view.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Route(value = "incarico-sede-mitt-dests/:id", layout = MainView.class)
@ViewController(id = "IncaricoSedeMittDest.detail")
@ViewDescriptor(path = "incarico-sede-mitt-dest-detail-view.xml")
@EditedEntityContainer("incaricoSedeMittDestDc")
public class IncaricoSedeMittDestDetailView extends StandardDetailView<IncaricoSedeMittDest> {
    @Autowired
    private DataManager dm;

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        IncaricoSedeMittDest entity = this.getEditedEntity();
        IncaricoSedeMittDest ismd = this.dm.load(IncaricoSedeMittDest.class)
                .query("select ismd from IncaricoSedeMittDest ismd where ismd.incarico.id = :id order by ismd.dal desc")
                .parameter("id", entity.getIncarico().getId())
                .optional().orElse(null);

        if(ismd!=null){
            IncaricoSedeMittDest ismddc = event.getDataContext().merge(ismd);
            ismddc.setAl(LocalDateTime.now());
        }
    }
}