package com.rafael.helpdesk.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafael.helpdesk.domain.model.Tecnico;
import com.rafael.helpdesk.dtos.TecnicoDTO;
import com.rafael.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos") // localhost:8080/tecnicos
public class TecnicoResource {

	/*
	 * Classe para relacionar a classe de serviço com as requisições
	 */

	@Autowired
	TecnicoService tecnicoService;

	// ResponseEntity é toda resposta HTTP, podendo controlar qualquer coisa, corpo,
	// cabeçalho, status....
	@GetMapping(value = "/{id}") // Ex: localhost:8080/tecnicos/1 - No caso o @PathVariable é o 1
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		Tecnico tecnico = tecnicoService.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(tecnico)); // Vai retornar o ResponseEntity, quando de o ok(),
																	// vai inserir o findById no body

	}

	// Quando não tem nenhum valor, o que valor é o valor do @RequestMapping que
	// colocamos na classe, para acessar esse metodos basta acessar: //
	// localhost:8080/tecnicos
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> findAll = tecnicoService.findAll();
		/* CConvertendo a lista de Tecnicos para uma lista de TecnicoDTO */
		List<TecnicoDTO> listaTodosTecnicoDTO = findAll.stream().map(tecnico -> new TecnicoDTO(tecnico))
				.collect(Collectors.toList());

		/*
		 * Adicionar uma listaTodosTecnicoDTO no corpo, quando tiver ok na chamada,
		 * então retornamos ResponseEntity
		 */
		return ResponseEntity.ok().body(listaTodosTecnicoDTO);

	}

}
