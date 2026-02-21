package com.andreacaggiani.frontendCargoflow.view.widget;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ClienteForm extends VerticalLayout {
    private TextField ragioneSociale;
    private TextField indirizzo;
    private TextField citta;
    private TextField cap;

    public ClienteForm() {
        ragioneSociale = new TextField("Ragione sociale");
        ragioneSociale.setRequired(true);

        indirizzo = new TextField("Indirizzo");
        indirizzo.setRequired(true);

        citta = new TextField("Città");
        citta.setRequired(true);

        cap = new TextField("CAP");
        cap.setRequired(true);

        HorizontalLayout rigaIndirizzo = new HorizontalLayout(indirizzo, citta, cap);
        add(ragioneSociale, rigaIndirizzo);
    }

    public String getRagioneSociale() { return ragioneSociale.getValue(); }
    public String getIndirizzo() { return indirizzo.getValue(); }
    public String getCitta() { return citta.getValue(); }
    public String getCap() { return cap.getValue(); }
}