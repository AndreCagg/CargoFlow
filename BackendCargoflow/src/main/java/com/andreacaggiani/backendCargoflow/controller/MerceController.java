package com.andreacaggiani.backendCargoflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andreacaggiani.backendCargoflow.entity.MerceTipo;
import com.andreacaggiani.backendCargoflow.repo.MerceTipoRepo;

@RestController
@RequestMapping("/api/v1/merce")
public class MerceController {
	@Autowired
	private MerceTipoRepo repoMerceTipo;
	
	@GetMapping("/tipi-disponibili")
	public ResponseEntity getTipiDisponibili() {
		List<MerceTipo> tipo=this.repoMerceTipo.findAll();
		
		
		return tipo.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tipo);
	}
}
