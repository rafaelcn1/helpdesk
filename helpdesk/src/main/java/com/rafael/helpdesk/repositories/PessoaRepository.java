package com.rafael.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafael.helpdesk.domain.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
