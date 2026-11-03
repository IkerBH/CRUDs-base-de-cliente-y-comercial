package org.iesvegademijas.tienda_informatica.dao;

import java.util.List;
import java.util.Optional;

import org.iesvegademijas.tienda_informatica.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void create(Cliente cliente) {
		jdbcTemplate.update(
				"INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoria) VALUES (?, ?, ?, ?, ?)",
				cliente.getNombre(), cliente.getApellido1(), cliente.getApellido2(), cliente.getCiudad(),
				cliente.getCategoria());
	}

	@Override
	public List<Cliente> getAll() {
		return jdbcTemplate.query("SELECT * FROM cliente ORDER BY id", (rs, rowNum) -> new Cliente(
				rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"),
				rs.getString("ciudad"), (Integer) rs.getObject("categoria")));
	}

	@Override
	public Optional<Cliente> find(int id) {
		List<Cliente> clientes = jdbcTemplate.query("SELECT * FROM cliente WHERE id = ?",
				(rs, rowNum) -> new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"),
						rs.getString("apellido2"), rs.getString("ciudad"), (Integer) rs.getObject("categoria")), id);
		return clientes.stream().findFirst();
	}

	@Override
	public void update(Cliente cliente) {
		jdbcTemplate.update(
				"UPDATE cliente SET nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, categoria = ? WHERE id = ?",
				cliente.getNombre(), cliente.getApellido1(), cliente.getApellido2(), cliente.getCiudad(),
				cliente.getCategoria(), cliente.getId());
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM cliente WHERE id = ?", id);
	}
}