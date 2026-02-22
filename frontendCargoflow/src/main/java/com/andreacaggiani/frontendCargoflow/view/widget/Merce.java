package com.andreacaggiani.frontendCargoflow.view.widget;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoIcon;

public class Merce extends VerticalLayout{
	private TextField elementoADR;
	private Select<String> codiciImballaggi;
	private TextField qtaADR;
	private Select<String> umQTA;
	private NumberField epalSN;
	
	
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
		epal.addValueChangeListener(listener->{
			epalSN.setVisible(listener.getValue());
		});
		
		HorizontalLayout rigaTipoMerce=new HorizontalLayout();
		VerticalLayout colFragileEpal=new VerticalLayout();
		colFragileEpal.add(fragile, epal);
		
		rigaTipoMerce.add(tipoMerce, colFragileEpal);
		
		epalSN=new NumberField("ID EPAL");
		epalSN.setVisible(epal.getValue());
		
		Checkbox adr=new Checkbox("ADR");
		elementoADR=new TextField("Elemento ADR");
		codiciImballaggi=new Select<>();
		qtaADR=new TextField("Qta");
		umQTA=new Select<>();
		this.setVisibility(adr.getValue());
		
		elementoADR.setPrefixComponent(LumoIcon.SEARCH.create());
		elementoADR.setPlaceholder("Cerca...");
		
		adr.addValueChangeListener(listener->{
			this.setVisibility(listener.getValue());
		});
		
		elementoADR.setVisible(adr.getValue());
		
		HorizontalLayout primaRigaADR=new HorizontalLayout();
		primaRigaADR.setAlignItems(Alignment.BASELINE);
		primaRigaADR.add(adr, elementoADR);
		
		codiciImballaggi.setLabel("Codice imballaggio");
		codiciImballaggi.setRequiredIndicatorVisible(true);
		codiciImballaggi.setVisible(adr.getValue());
		
		qtaADR.setRequired(true);
		
		umQTA.setLabel("UM");
		umQTA.setRequiredIndicatorVisible(true);
		
		HorizontalLayout infoADR=new HorizontalLayout();
		infoADR.add(qtaADR, umQTA);
		
		add(segnacollo, rigaPesoVolumetrico, rigaTipoMerce, epalSN, primaRigaADR, codiciImballaggi, infoADR);
	}
	
	private void setVisibility(Boolean visibility) {
		elementoADR.setVisible(visibility);
		codiciImballaggi.setVisible(visibility);
		qtaADR.setVisible(visibility);
		umQTA.setVisible(visibility);
	}
}
