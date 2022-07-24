package com.prueba.controller;

import com.prueba.model.Cliente;
import com.prueba.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping("/crear")
    public Cliente crearCliente(@RequestBody Cliente cliente){
        return clienteService.guardarCliente(cliente);
    }

    @PutMapping("/actualizar")
    public Cliente actualizarCliente(@RequestBody Cliente cliente){
        return clienteService.guardarCliente(cliente);
    }

    @DeleteMapping("/eliminar/{id}")
    public String elimarCliente(@PathVariable("id") long id){
        if(clienteService.eliminarClientePorId(id))
            return "Cliente eliminado";
        else{
            return "No se ha encontrado al cliente "+id;
        }
    }

}
