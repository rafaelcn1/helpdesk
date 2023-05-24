package com.rafael.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.helpdesk.domain.model.Tecnico;
import com.rafael.helpdesk.repositories.TecnicoRepository;
import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	/*
	 * Classe para conversar com o banco
	 * */

	@Autowired
	TecnicoRepository tecnicoRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> findById = tecnicoRepository.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto do ID: " + id + " - Não encontrado!"));
	}

}
