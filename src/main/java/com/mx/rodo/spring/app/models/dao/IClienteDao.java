package com.mx.rodo.spring.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mx.rodo.spring.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
	
	/*
	 * public List<Cliente> findAll();
	 * 
	 * public void save(Cliente cliente);
	 * 
	 * public Cliente findOne(Long id);
	 * 
	 * public void delete(Long id);
	 * 
	 * todo esto sin el extends
	 */

}
