package com.rafael.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafael.helpdesk.domain.model.Cliente;
import com.rafael.helpdesk.domain.model.Pessoa;
import com.rafael.helpdesk.dtos.ClienteDTO;
import com.rafael.helpdesk.repositories.ClienteRepository;
import com.rafael.helpdesk.repositories.PessoaRepository;
import com.rafael.helpdesk.services.execptions.DataIntegrityViolationException;
import com.rafael.helpdesk.services.execptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service // permite a implementação de lógica de negócio e regras de processamento de
			// dados
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	PessoaRepository pessoaRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto do ID: " + id + " - Não encontrado!"));

	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		validarCpfEEmail(clienteDTO);

		Cliente novoCliente = new Cliente(clienteDTO);
		return clienteRepository.save(novoCliente);
	}

	public void validarCpfEEmail(ClienteDTO clienteDTO) {
		Optional<Pessoa> cliente = pessoaRepository.findByCpf(clienteDTO.getCpf());

		if (cliente.isPresent() && cliente.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("CPF Já Cadastrado!");
		}

		cliente = pessoaRepository.findByEmail(clienteDTO.getEmail());
		if (cliente.isPresent() && cliente.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("Email Já Cadastrado!");
		}

	}

	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		Cliente clienteAntigo = findById(id);
		validarCpfEEmail(clienteDTO);
		clienteAntigo = new Cliente(clienteDTO);
		return clienteRepository.save(clienteAntigo);

	}

}
