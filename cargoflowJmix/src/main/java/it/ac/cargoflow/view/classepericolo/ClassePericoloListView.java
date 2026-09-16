package it.ac.cargoflow.view.classepericolo;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.entity.ClassePericolo;
import it.ac.cargoflow.view.main.MainView;


@Route(value = "classe-pericoloes", layout = MainView.class)
@ViewController(id = "ClassePericolo.list")
@ViewDescriptor(path = "classe-pericolo-list-view.xml")
@LookupComponent("classePericoloesDataGrid")
@DialogMode(width = "64em")
public class ClassePericoloListView extends StandardListView<ClassePericolo> {
}