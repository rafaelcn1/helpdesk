package com.rafael.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.helpdesk.domain.model.Tecnico;
import com.rafael.helpdesk.dtos.TecnicoDTO;
import com.rafael.helpdesk.repositories.TecnicoRepository;
import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	/*
	 * Classe para conversar com o banco
	 */

	@Autowired
	TecnicoRepository tecnicoRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> findById = tecnicoRepository.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto do ID: " + id + " - Não encontrado!"));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null); //Por questão  de segurança, o id sempre vai vir nulo, senao irar atualizar o que já existe
		Tecnico novoTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(novoTecnico);
	}

}
