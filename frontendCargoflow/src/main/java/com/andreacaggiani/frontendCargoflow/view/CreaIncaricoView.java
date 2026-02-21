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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
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
	public CreaIncaricoView() {
		HorizontalLayout primaRiga=new HorizontalLayout();
		TextField ldv=new TextField("LDV");
		ldv.setRequired(true);
		
		Checkbox ritiro=new Checkbox();
		ritiro.setLabel("RITIRO");
		
		primaRiga.add(ldv, ritiro);
		
		HorizontalLayout rigaClienti=new HorizontalLayout();
		ClienteForm mitt=new ClienteForm();
		ClienteForm dest=new ClienteForm();
		
		rigaClienti.add(mitt, dest);
		
		HorizontalLayout rigaVettori=new HorizontalLayout();
		ClienteForm vettoreMitt=new ClienteForm();
		ClienteForm vettoreDest=new ClienteForm();
		
		Accordion sezioneVettori=new Accordion();
		rigaVettori.add(vettoreMitt, vettoreDest);
		
		sezioneVettori.add("Vettori", rigaVettori);
		
		
		Accordion sezioneConsegna = new Accordion();
		HorizontalLayout consegnaValori=new HorizontalLayout();
		
		
		//da mettere DATA RITIRO
		DatePicker dataConsegna=new DatePicker("Data consegna prevista");
		dataConsegna.setRequired(false);
		
		NumberField valoreDoganale=new NumberField("Valore doganale");
		valoreDoganale.setRequired(false);
		
		NumberField valoreAssicurazione=new NumberField("Valore assicurazione");
		valoreAssicurazione.setRequired(false);
		
		consegnaValori.add(dataConsegna, valoreDoganale, valoreAssicurazione);
		
		HorizontalLayout rigaContrassegno=new HorizontalLayout();
		Checkbox contrassegno=new Checkbox("Contrassegno");
		Select<String> tipoContrassegno=new Select<>();
		tipoContrassegno.setLabel("Tipo contrassegno");
		rigaContrassegno.add(contrassegno, tipoContrassegno);
		
		NumberField contrassegnoValore=new NumberField("Valore contrassegno");
		
		VerticalLayout fascePreferiteLayout=new VerticalLayout();
		HorizontalLayout fasceOrarieItems=new HorizontalLayout();
		List<FasciaOraria> fasceOrarie=new ArrayList<>();
		fasceOrarie.add(new FasciaOraria());
		for(FasciaOraria fo : fasceOrarie) {
			fasceOrarieItems.add(fo);
		}
		
		Button nuovaFasciaOraria=new Button(LumoIcon.PLUS.create());
		fasceOrarieItems.add(nuovaFasciaOraria);
		
		fascePreferiteLayout.add(new Text("Fasce orarie preferite"), fasceOrarieItems);
		VerticalLayout dettagliConsegna=new VerticalLayout(consegnaValori, rigaContrassegno, contrassegnoValore, fascePreferiteLayout);
		sezioneConsegna.add("Dettagli consegna", dettagliConsegna); //DETTAGLI RITIRO
		
		List<Merce> merci=new ArrayList<>();
		merci.add(new Merce());
		
		HorizontalLayout merceLayout=new HorizontalLayout();
		for(Merce m : merci) {
			merceLayout.add(m);
		}
		
		Button aggiungiMerce=new Button(LumoIcon.PLUS.create());
		merceLayout.add(aggiungiMerce);
		
		add(primaRiga, rigaClienti, sezioneVettori, sezioneConsegna, merceLayout);
	}
}
