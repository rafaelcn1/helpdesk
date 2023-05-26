package com.rafael.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.helpdesk.domain.model.Chamado;
import com.rafael.helpdesk.dtos.ChamadoDTO;
import com.rafael.helpdesk.repositories.ChamadoRepository;
import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	public Chamado findById(Integer id) {
		Optional<Chamado> findById = chamadoRepository.findById(id);

		return findById.orElseThrow(() -> new ObjectNotFoundException("Objeto do ID: " + id + " - Não encontrado!"));

	}

	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}

	public Chamado create(ChamadoDTO chamadoDTO) {
		chamadoDTO.setId(null);
		Chamado novoChamado = new Chamado(chamadoDTO);
		return chamadoRepository.save(novoChamado);
	}

}
