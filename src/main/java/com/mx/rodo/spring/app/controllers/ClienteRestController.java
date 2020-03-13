package com.mx.rodo.spring.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.rodo.spring.app.models.service.IClienteService;
import com.mx.rodo.spring.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api/clientes")
@Secured("ROLE_ADMIN")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value = "/listar")
	public ClienteList listar() {
		return new ClienteList(clienteService.findAll());
	}
}
