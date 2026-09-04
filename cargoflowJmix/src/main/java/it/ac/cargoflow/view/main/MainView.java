package it.ac.cargoflow.view.main;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import io.jmix.core.session.SessionData;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.main.ListMenu;
import io.jmix.flowui.kit.component.menu.MenuItem;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import it.ac.cargoflow.app.AziendaSedeContext;
import it.ac.cargoflow.conf.Costants;
import it.ac.cargoflow.entity.Azienda;
import it.ac.cargoflow.entity.Sede;
import it.ac.cargoflow.entity.User;
import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import it.ac.cargoflow.view.user.UserDetailView;
import it.ac.cargoflow.view.user.UserListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route("")
@ViewController(id = "MainView")
@ViewDescriptor(path = "main-view.xml")
public class MainView extends StandardMainView {
    @Autowired
    private Messages messages;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @ViewComponent
    private Span aziendaSede;

    @Autowired
    private SessionData session;

    @ViewComponent
    private EntityComboBox<Sede> sede;
    @ViewComponent
    private EntityComboBox<Azienda> azienda;
    @ViewComponent
    private CollectionContainer<Azienda> aziendasDc;

    @Autowired
    private Dialogs dialogs;

    @Autowired
    private AziendaSedeContext asc;

    @ViewComponent
    private ListMenu menu;

    private Boolean sceltaConfermata;
    @ViewComponent
    private JmixButton modifica;
    private Logger log = LoggerFactory.getLogger(MainView.class);

    public void setAziendaSede(Azienda a, Sede s){
        String titolo = "";

        if (a != null) {
            titolo = a.getDenominazione().toUpperCase()+(s!=null ? " - "+s.getNome().toUpperCase() : "");
        }

        this.aziendaSede.setText(titolo);
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        Azienda a = asc.getAzienda();
        Sede s = asc.getSede();

        Boolean datiPresenti = a!=null && s!=null;

        sceltaConfermata=datiPresenti;
        if(datiPresenti){
            azienda.setValue(a);
            sede.setValue(s);
            confermaHandler(a, s);
        }

        aggiornaMenu();
        UI.getCurrent().addBeforeEnterListener(this::handleBeforeEnter);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if(aziendasDc.getItems().size()==1){
            azienda.setValue(aziendasDc.getItems().getFirst());
            azienda.setReadOnly(true);
        }

        modifica.setVisible(sceltaConfermata && modificaVisibile());
        Azienda a2 = asc.getAzienda();
        Sede s = asc.getSede();

        Boolean datiPresenti = a2!=null && s!=null;
        sceltaConfermata = datiPresenti || !sceltaNecessaria();
        aggiornaMenu();
    }

    @Subscribe(id = "modifica", subject = "clickListener")
    public void onModificaClick(final ClickEvent<JmixButton> event) {
        sceltaConfermata=false;
        asc.setAziendaSedeCtx(null, null);
        aggiornaMenu();
        azienda.setReadOnly(false);
        sede.setReadOnly(!(azienda.getValue().getSede().size()>1));
        modifica.setVisible(false);
    }

    private Boolean modificaVisibile(){
        return aziendasDc.getItems().size()>1 || azienda.getValue().getSede().size()>1;
    }

    @Install(to = "userMenu", subject = "buttonRenderer")
    private Component userMenuButtonRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        String userName = generateUserName(user);

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-button-content");

        Avatar avatar = createAvatar(userName);

        Span name = uiComponents.create(Span.class);
        name.setText(userName);
        name.setClassName("user-menu-text");

        content.add(avatar, name);

        if (isSubstituted(user)) {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(messages.getMessage("userMenu.substituted"));
            subtext.setClassName("user-menu-subtext");

            content.add(subtext);
        }

        return content;
    }

    @Install(to = "userMenu", subject = "headerRenderer")
    private Component userMenuHeaderRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-header-content");

        String name = generateUserName(user);

        Avatar avatar = createAvatar(name);
        avatar.addThemeVariants(AvatarVariant.LUMO_LARGE);

        Span text = uiComponents.create(Span.class);
        text.setText(name);
        text.setClassName("user-menu-text");

        content.add(avatar, text);

        if (name.equals(user.getUsername())) {
            text.addClassNames("user-menu-text-subtext");
        } else {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(user.getUsername());
            subtext.setClassName("user-menu-subtext");

            content.add(subtext);
        }

        return content;
    }

    private Avatar createAvatar(String fullName) {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setName(fullName);
        avatar.getElement().setAttribute("tabindex", "-1");
        avatar.setClassName("user-menu-avatar");

        return avatar;
    }

    private String generateUserName(User user) {
        String userName = String.format("%s %s",
                        Strings.nullToEmpty(user.getFirstName()),
                        Strings.nullToEmpty(user.getLastName()))
                .trim();

        return userName.isEmpty() ? user.getUsername() : userName;
    }

    private boolean isSubstituted(User user) {
        UserDetails authenticatedUser = currentUserSubstitution.getAuthenticatedUser();
        return user != null && !authenticatedUser.getUsername().equals(user.getUsername());
    }

    @Subscribe("azienda")
    public void onAziendaComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<?>, ?> event) {
        Azienda a = azienda.getValue();
        List<Sede> sedi = null;
        if(a!=null){
            sedi = a.getSede();
        }else{
            sede.setItems(List.of());
        }

        sede.setReadOnly(false);

        if(sedi!=null) {
            sede.setItems(sedi);

            if (sedi.size() == 1) {
                sede.setValue(sedi.getFirst());
                sede.setReadOnly(true);
            }
        }
    }

    @Subscribe("sede")
    public void onSedeComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<?>, ?> event) {
        Azienda a = azienda.getValue();
        Sede s = sede.getValue();
        setAziendaSede(a, s);

        if(a!=null && s!=null){
            confermaAziendaSede(a, s);
        }
    }

    private Boolean sceltaNecessaria(){
        return !(aziendasDc.getItems().size()==1 && azienda.getValue().getSede().size()==1);
    }

    private void confermaAziendaSede(Azienda a, Sede s){
        if(sceltaConfermata || !sceltaNecessaria()) return;

        dialogs.createOptionDialog()
                .withHeader("Hai scelto")
                .withText("Vuoi confermare la scelta di "+a.getDenominazione().toUpperCase()+" - "+s.getNome().toUpperCase())
                .withActions(
                        new DialogAction(DialogAction.Type.YES).withHandler(e -> {
                            confermaHandler(a, s);

                            aggiornaMenu();
                        }),
                        new DialogAction(DialogAction.Type.NO)
                ).open();
    }

    private void confermaHandler(Azienda a, Sede s){
        azienda.setReadOnly(true);
        sede.setReadOnly(true);
        sceltaConfermata = true;
        modifica.setVisible(modificaVisibile());
        asc.setAziendaSedeCtx(a, s);
    }

    /*private void aggiornaMenu() {
        //menu.setVisible(sceltaConfermata);
        ListMenu.MenuItem users = menu.getMenuItem("User.list");
        ListMenu.MenuItem az = menu.getMenuItem("Azienda.list");
        ListMenu.MenuItem se = menu.getMenuItem("Sede.list");

        ListMenu.MenuItem applicationMenu = menu.getMenuItem("application");

        if (applicationMenu instanceof ListMenu.MenuBarItem parent) {
            for (ListMenu.MenuItem child : parent.getChildren()) {
                if (!child.equals(users) && !child.equals(az) && !child.equals(se)) {
                    child.setVisible(sceltaConfermata);
                    log.warn("{}, {}, isVisible-dopo={}, class={}",
                            child.getId(), sceltaConfermata, child.isVisible(), child.getClass());
                }
            }
        }
    }*/

    private void aggiornaMenu() {
        ListMenu.MenuItem users = menu.getMenuItem("User.list");
        ListMenu.MenuItem az = menu.getMenuItem("Azienda.list");
        ListMenu.MenuItem se = menu.getMenuItem("Sede.list");

        ListMenu.MenuItem applicationMenu = menu.getMenuItem("application");

        if (applicationMenu instanceof ListMenu.MenuBarItem parent) {
            List<ListMenu.MenuItem> children = new ArrayList<>(parent.getChildren());

            for (ListMenu.MenuItem child : children) {
                if (!child.equals(users) && !child.equals(az) && !child.equals(se)) {
                    child.setVisible(sceltaConfermata);

                    int idx = children.indexOf(child);
                    parent.removeChildItem(child);
                    parent.addChildItem(child, idx);
                }
            }
        }
    }

    private void handleBeforeEnter(BeforeEnterEvent event) {
        Class<?> targetView = event.getNavigationTarget(); //da escludere quelli attivi

        if (!MainView.class.isAssignableFrom(targetView) && !UserListView.class.isAssignableFrom(targetView) && !sceltaConfermata) {
            event.forwardTo(MainView.class);
        }
    }

    /*@Override
    public void beforeEnter(BeforeEnterEvent event) {
        Class<?> targetView = event.getNavigationTarget();

        // 1. Viste sempre permesse
        boolean isAllowedView = MainView.class.isAssignableFrom(targetView)
                || UserListView.class.isAssignableFrom(targetView);

        if (isAllowedView) {
            return;
        }

        // 2. Controllo rapido sullo stato salvato in sessione (senza toccare la UI)
        Azienda a = asc.getAzienda();
        Sede s = asc.getSede();
        boolean datiInSessioneMancanti = (a == null || s == null);

        // 3. Se mancano i dati in sessione e l'utente non ha ancora confermato la scelta
        if (datiInSessioneMancanti && !Boolean.TRUE.equals(sceltaConfermata)) {
            log.warn("Navigazione bloccata verso {}: Azienda/Sede non selezionate.", targetView.getCanonicalName());
            event.forwardTo(MainView.class);
        }
    }*/
}
