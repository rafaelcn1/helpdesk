package com.rafael.helpdesk.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafael.helpdesk.domain.model.Tecnico;
import com.rafael.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos") // localhost:8080/tecnicos
public class TecnicoResource {
	
	/*
	 * Classe para relacionar a classe de serviço com as requisições
	 * */

	@Autowired
	TecnicoService tecnicoService;
	
	// ResponseEntity é toda resposta HTTP, podendo controlar qualquer coisa, corpo,
	// cabeçalho, status....
	@GetMapping(value = "/{id}") // Ex: localhost:8080/tecnicos/1 - No caso o @PathVariable é o 1
	public ResponseEntity<Tecnico> findById(@PathVariable Integer id) {
		Tecnico findById = tecnicoService.findById(id);
		return ResponseEntity.ok().body(findById); //Vai retornar o ResponseEntity, quando de o ok(), vai inserir o findById no body 

	}

}
