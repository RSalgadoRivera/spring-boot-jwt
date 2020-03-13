package com.mx.rodo.spring.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mx.rodo.spring.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
}
