package com.prueba.controller;

import com.prueba.dto.ConsultarMovimientos;
import com.prueba.dto.MovimientoDTO;
import com.prueba.model.Movimiento;
import com.prueba.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    MovimientoService movimientoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody MovimientoDTO movimientoDTO){
        String movimiento = movimientoService.guardarMovimiento(movimientoDTO);
        return new ResponseEntity(movimiento, HttpStatus.OK);
    }

    @GetMapping("/reportes")
    public ResponseEntity<?>  obtenerMovimientosPorFecha(@RequestParam(name = "fechaInicio") String fechaInicio,
                                                         @RequestParam(name = "fechaFinal") String fechaFinal,
                                                         @RequestParam(name = "idCliente") long idCliente){

        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorClienteYFecha(new ConsultarMovimientos(idCliente, fechaInicio, fechaFinal));
        return new ResponseEntity(movimientos,HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarMovimiento(@PathVariable("id") long id){
        if(movimientoService.eliminarMovimientoPorId(id))
            return "Se ha eliminado el movimiento";
        else
            return "No se ha eliminado el movimiento";
    }

}
