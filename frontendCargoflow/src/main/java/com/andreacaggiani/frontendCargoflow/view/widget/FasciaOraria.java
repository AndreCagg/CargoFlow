package com.andreacaggiani.frontendCargoflow.view.widget;

import java.time.Duration;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

public class FasciaOraria extends HorizontalLayout{
	public FasciaOraria() {
		TimePicker dalle=new TimePicker("Dalle");
		dalle.setStep(Duration.ofMinutes(30));
		
		TimePicker alle=new TimePicker("Alle");
		alle.setStep(Duration.ofMinutes(30));
		
		add(dalle, alle);
	}
}
