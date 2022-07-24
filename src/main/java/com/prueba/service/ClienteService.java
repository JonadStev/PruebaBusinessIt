package com.prueba.service;

import com.prueba.model.Cliente;

import java.util.ArrayList;
import java.util.Optional;

public interface ClienteService {

    Cliente guardarCliente(Cliente cliente);
    ArrayList<Cliente> obtenerClientes();
    Optional<Cliente> obtenerClientePorId(long id);

    boolean eliminarClientePorId(long id);
}
