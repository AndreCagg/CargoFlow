package com.andreacaggiani.backendCargoflow.dto.common;

import java.util.UUID;

public class FasciaOraria {
	private UUID giorno;
	private FasciaOrariaIntervallo fasciaOraria;
	private Boolean ritiro;
	
	
	public UUID getGiorno() {
		return giorno;
	}
	public void setGiorno(UUID giorno) {
		this.giorno = giorno;
	}
	public FasciaOrariaIntervallo getFasciaOraria() {
		return fasciaOraria;
	}
	public void setFasciaOraria(FasciaOrariaIntervallo fasciaOraria) {
		this.fasciaOraria = fasciaOraria;
	}
	public Boolean getRitiro() {
		return ritiro;
	}
	public void setRitiro(Boolean ritiro) {
		this.ritiro = ritiro;
	}
}
