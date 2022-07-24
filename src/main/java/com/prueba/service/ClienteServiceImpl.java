package com.prueba.service;

import com.prueba.model.Cliente;
import com.prueba.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public ArrayList<Cliente> obtenerClientes() {
        return (ArrayList<Cliente>) clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> obtenerClientePorId(long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public boolean eliminarClientePorId(long id) {
        try {
            Optional<Cliente> c = obtenerClientePorId(id);
            clienteRepository.delete(c.get());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
