package com.prueba.controller;

import com.prueba.dto.CuentaDTO;
import com.prueba.model.Cuenta;
import com.prueba.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody CuentaDTO cuentaDTO){
        Cuenta cuenta = cuentaService.guardarCuenta(cuentaDTO);
        if(cuenta == null || cuenta.equals(null))
            return new ResponseEntity("No se ha podido crear la cuenta de "+cuentaDTO.getTipoCuenta()+" al cliente "+ cuentaDTO.getIdCliente(), HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(cuenta, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarCuenta(@RequestBody CuentaDTO cuentaDTO){
        Cuenta cuenta = cuentaService.guardarCuenta(cuentaDTO);
        if(cuenta == null || cuenta.equals(null))
            return new ResponseEntity("No se ha podido actualizar la cuenta de "+cuentaDTO.getTipoCuenta()+" al cliente "+ cuentaDTO.getIdCliente(), HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(cuenta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarCuenta(@PathVariable("id") long id){
        if(cuentaService.eliminarCuentaPorId(id))
            return "Se ha eliminado la cuenta";
        else
            return "No se ha podido eliminar la cuenta o la cuenta no existe";
    }

}
