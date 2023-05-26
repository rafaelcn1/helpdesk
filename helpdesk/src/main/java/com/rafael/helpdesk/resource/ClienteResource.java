package com.rafael.helpdesk.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafael.helpdesk.domain.model.Cliente;
import com.rafael.helpdesk.dtos.ClienteDTO;
import com.rafael.helpdesk.services.ClienteService;

@RestController // permite que métodos retornem diretamente objetos convertidos em JSON/XML
@RequestMapping(value = "/clientes") // Mapeamento de uma URL para um método específico em uma classe de controle
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente cliente = clienteService.findById(id);
		return ResponseEntity.ok().body(new ClienteDTO(cliente));

	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> findAll = clienteService.findAll();
		List<ClienteDTO> findAllClienteDTO = findAll.stream().map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(findAllClienteDTO);

	}

}
