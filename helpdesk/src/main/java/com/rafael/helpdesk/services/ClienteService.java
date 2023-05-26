package com.rafael.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.helpdesk.domain.model.Cliente;
import com.rafael.helpdesk.dtos.ClienteDTO;
import com.rafael.helpdesk.repositories.ClienteRepository;
import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

@Service // permite a implementação de lógica de negócio e regras de processamento de
			// dados
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto do ID: " + id + " - Não encontrado!"));

	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		Cliente novoCliente = new Cliente(clienteDTO);
		return clienteRepository.save(novoCliente);
	}

}
