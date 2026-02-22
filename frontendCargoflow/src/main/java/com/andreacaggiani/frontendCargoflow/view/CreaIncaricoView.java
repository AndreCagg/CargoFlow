package com.andreacaggiani.frontendCargoflow.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.andreacaggiani.frontendCargoflow.view.widget.ClienteForm;
import com.andreacaggiani.frontendCargoflow.view.widget.FasciaOraria;
import com.andreacaggiani.frontendCargoflow.view.widget.Merce;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.FieldSet;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

@Route("crea-incarico")
public class CreaIncaricoView extends VerticalLayout{
	private String titoloSezioneConsegna;
	private AccordionPanel panelConsegna;
	private DatePicker dataRitiro;
	private VerticalLayout fasceRitiroPreferiteLayout;
	
	public CreaIncaricoView() {
		HorizontalLayout primaRiga=new HorizontalLayout();
		primaRiga.setAlignItems(Alignment.BASELINE);
		TextField ldv=new TextField("LDV");
		ldv.setRequired(true);
		
		
		Checkbox ritiro=new Checkbox();
		ritiro.setLabel("RITIRO");
		ritiro.addValueChangeListener(listener->{
			this.aggiornaTitoloConsegna(listener.getValue());
		});
		
		primaRiga.add(ldv, ritiro);
		
		HorizontalLayout rigaClienti=new HorizontalLayout();
		
		VerticalLayout mittLayout=new VerticalLayout();
		VerticalLayout destLayout=new VerticalLayout();
		Text titoloMitt=new Text("Mittente");
		Text titoloDest=new Text("Destinatario");
		
		ClienteForm mitt=new ClienteForm();
		ClienteForm dest=new ClienteForm();
		
		mittLayout.add(titoloMitt, mitt);
		destLayout.add(titoloDest, dest);
		
		Accordion accClienti=new Accordion();
		rigaClienti.add(mittLayout, destLayout);
		
		FieldSet fieldsetClienti=new FieldSet(rigaClienti);
		
		accClienti.add("Mittente e destinatario", fieldsetClienti);
		
		HorizontalLayout rigaVettori=new HorizontalLayout();
		VerticalLayout vettoreMittLayout=new VerticalLayout();
		VerticalLayout vettoreDestLayout=new VerticalLayout();
		
		ClienteForm vettoreMitt=new ClienteForm();
		ClienteForm vettoreDest=new ClienteForm();
		Text titoloVettoreMitt=new Text("Vettore mittente");
		Text titoloVettoreDest=new Text("Vettore destinatario");
		
		vettoreMittLayout.add(titoloVettoreMitt, vettoreMitt);
		vettoreDestLayout.add(titoloVettoreDest, vettoreDest);
		
		Accordion sezioneVettori=new Accordion();
		rigaVettori.add(vettoreMittLayout, vettoreDestLayout);
		
		FieldSet fieldsetVettori=new FieldSet(rigaVettori);
		
		sezioneVettori.add("Vettori", fieldsetVettori);
		
		
		HorizontalLayout consegnaValori=new HorizontalLayout();
		
		
		DatePicker dataConsegna=new DatePicker("Data consegna prevista");
		dataConsegna.setRequired(false);
		
		dataRitiro=new DatePicker("Data ritiro prevista");
		dataRitiro.setRequired(false);
		dataRitiro.setVisible(ritiro.getValue());
		
		NumberField valoreDoganale=new NumberField("Valore doganale");
		valoreDoganale.setRequired(false);
		
		NumberField valoreAssicurazione=new NumberField("Valore assicurazione");
		valoreAssicurazione.setRequired(false);
		
		consegnaValori.add(dataRitiro, dataConsegna, valoreDoganale, valoreAssicurazione);
		
		HorizontalLayout rigaContrassegno=new HorizontalLayout();
		rigaContrassegno.setAlignItems(Alignment.BASELINE);
		
		NumberField contrassegnoValore=new NumberField("Valore contrassegno");
		Checkbox contrassegno=new Checkbox("Contrassegno");
		
		contrassegno.addValueChangeListener(listener->{
			contrassegnoValore.setVisible(listener.getValue());
		});
		
		contrassegnoValore.setVisible(contrassegno.getValue());
		
		Select<String> tipoContrassegno=new Select<>();
		tipoContrassegno.setLabel("Tipo contrassegno");
		
		rigaContrassegno.add(contrassegno);
		
		
		VerticalLayout fasceConsegnaPreferiteLayout=new VerticalLayout();
		fasceRitiroPreferiteLayout=new VerticalLayout();
		
		HorizontalLayout fasceOrarieConsegnaItems=new HorizontalLayout();
		HorizontalLayout fasceOrarieRitiroItems=new HorizontalLayout();
		//List<FasciaOraria> fasceOrarie=new ArrayList<>();
		
		fasceOrarieConsegnaItems.add(new FasciaOraria());
		fasceOrarieRitiroItems.add(new FasciaOraria());
		
		Button nuovaFasciaOrariaConsegna=new Button(LumoIcon.PLUS.create());
		fasceOrarieConsegnaItems.setAlignItems(Alignment.BASELINE);
		fasceOrarieConsegnaItems.add(nuovaFasciaOrariaConsegna);
		
		Button nuovaFasciaOrariaRitiro=new Button(LumoIcon.PLUS.create());
		fasceOrarieRitiroItems.setAlignItems(Alignment.BASELINE);
		fasceOrarieRitiroItems.add(nuovaFasciaOrariaRitiro);
		
		fasceConsegnaPreferiteLayout.add(new Text("Fasce orarie di consegna preferite"), fasceOrarieConsegnaItems);
		fasceRitiroPreferiteLayout.add(new Text("Fasce orarie di ritiro preferite"), fasceOrarieRitiroItems);
		fasceRitiroPreferiteLayout.setVisible(ritiro.getValue());
		
		VerticalLayout dettagliConsegna=new VerticalLayout(consegnaValori, rigaContrassegno, contrassegnoValore, fasceConsegnaPreferiteLayout, fasceRitiroPreferiteLayout);
		
		this.aggiornaTitoloConsegna(ritiro.getValue());
		
		Accordion sezioneConsegna = new Accordion();
		panelConsegna=sezioneConsegna.add(titoloSezioneConsegna, dettagliConsegna); //DETTAGLI RITIRO
		
		FieldSet fieldsetConsegna=new FieldSet(sezioneConsegna);
		
		List<Merce> merci=new ArrayList<>();
		merci.add(new Merce());
		
		HorizontalLayout merceLayout=new HorizontalLayout();
		Accordion sezioneMerce=new Accordion();
		merceLayout.setAlignItems(Alignment.CENTER);
		for(Merce m : merci) {
			merceLayout.add(new FieldSet(m));
		}
		
		Button aggiungiMerce=new Button(LumoIcon.PLUS.create());
		merceLayout.add(aggiungiMerce);
		
		
		FieldSet merce=new FieldSet(merceLayout);
		sezioneMerce.add("Merce", merce);
		
		add(primaRiga, accClienti, sezioneVettori, fieldsetConsegna, sezioneMerce);
	}
	
	private void aggiornaTitoloConsegna(Boolean ritiro) {
		titoloSezioneConsegna= !ritiro ? "Dettagli consegna" : "Dettagli ritiro e consegna";
		if(panelConsegna!=null) {
			panelConsegna.setSummaryText(titoloSezioneConsegna);
		}
		
		dataRitiro.setVisible(ritiro);
		fasceRitiroPreferiteLayout.setVisible(ritiro);
	}
}
