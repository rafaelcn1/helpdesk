package com.rafael.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafael.helpdesk.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
