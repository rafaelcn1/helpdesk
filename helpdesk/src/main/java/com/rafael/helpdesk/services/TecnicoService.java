package com.rafael.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.helpdesk.domain.model.Pessoa;
import com.rafael.helpdesk.domain.model.Tecnico;
import com.rafael.helpdesk.dtos.TecnicoDTO;
import com.rafael.helpdesk.repositories.PessoaRepository;
import com.rafael.helpdesk.repositories.TecnicoRepository;
import com.rafael.helpdesk.services.execptions.DataIntegrityViolationException;
import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	/*
	 * Classe para conversar com o banco
	 */

	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> findById = tecnicoRepository.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto do ID: " + id + " - Não encontrado!"));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null); //Por questão  de segurança, o id sempre vai vir nulo, senao irar atualizar o que já existe
		
		validarCpfEEmail(tecnicoDTO);
		
		Tecnico novoTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(novoTecnico);
	}

	private void validarCpfEEmail(TecnicoDTO tecnicoDTO) {
		Optional<Pessoa> tecnico = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
		
		if(tecnico.isPresent() && tecnico.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrityViolationException("CPF Já Cadastrado!");
		}
		
		tecnico = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
		if(tecnico.isPresent() && tecnico.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrityViolationException("Email Já Cadastrado!");
		}
		
	}

}
