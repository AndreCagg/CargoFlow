package it.ac.cargoflow.view.classepericolo;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import it.ac.cargoflow.entity.ClassePericolo;
import it.ac.cargoflow.view.main.MainView;

@Route(value = "classe-pericoloes/:id", layout = MainView.class)
@ViewController(id = "ClassePericolo.detail")
@ViewDescriptor(path = "classe-pericolo-detail-view.xml")
@EditedEntityContainer("classePericoloDc")
public class ClassePericoloDetailView extends StandardDetailView<ClassePericolo> {
}