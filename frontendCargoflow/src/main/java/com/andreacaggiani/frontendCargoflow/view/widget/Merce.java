package com.andreacaggiani.frontendCargoflow.view.widget;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoIcon;

public class Merce extends VerticalLayout{
	public Merce() {
		TextField segnacollo=new TextField("ID merce");
		segnacollo.setRequired(true);
		
		NumberField peso=new NumberField("Peso kg");
		peso.setRequired(true);
		
		NumberField volume=new NumberField("Volume m3");
		volume.setRequired(true);
		
		HorizontalLayout rigaPesoVolumetrico=new HorizontalLayout();
		rigaPesoVolumetrico.add(peso, volume);
		
		Select<String> tipoMerce=new Select<>();
		tipoMerce.setLabel("Tipo merce");
		tipoMerce.setRequiredIndicatorVisible(true);
		
		Checkbox fragile=new Checkbox("FRAGILE");
		Checkbox epal=new Checkbox("EPAL");
		
		HorizontalLayout rigaTipoMerce=new HorizontalLayout();
		VerticalLayout colFragileEpal=new VerticalLayout();
		colFragileEpal.add(fragile, epal);
		
		rigaTipoMerce.add(tipoMerce, colFragileEpal);
		
		Checkbox adr=new Checkbox("ADR");
		TextField elementoADR=new TextField("Elemento ADR");
		elementoADR.setPrefixComponent(LumoIcon.SEARCH.create());
		elementoADR.setPlaceholder("Cerca...");
		
		HorizontalLayout primaRigaADR=new HorizontalLayout();
		primaRigaADR.add(adr, elementoADR);
		
		Select<String> codiciImballaggi=new Select<>();
		codiciImballaggi.setLabel("Codice imballaggio");
		codiciImballaggi.setRequiredIndicatorVisible(true);
		
		TextField qtaADR=new TextField("Qta");
		qtaADR.setRequired(true);
		
		Select<String> umQTA=new Select<>();
		umQTA.setLabel("UM");
		umQTA.setRequiredIndicatorVisible(true);
		
		HorizontalLayout infoADR=new HorizontalLayout();
		infoADR.add(qtaADR, umQTA);
		
		add(segnacollo, rigaPesoVolumetrico, rigaTipoMerce, primaRigaADR, codiciImballaggi, infoADR);
	}
}
