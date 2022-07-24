package com.prueba.service;

import com.prueba.dto.ConsultarMovimientos;
import com.prueba.dto.MovimientoDTO;
import com.prueba.model.Cuenta;
import com.prueba.model.Movimiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MovimientoService {

    String guardarMovimiento(MovimientoDTO movimientoDTO);

    ArrayList<Movimiento> obtenerMovimientos();

    List<Movimiento> obtenerMovimientosPorClienteYFecha(ConsultarMovimientos consultarMovimientos);

    Optional<Movimiento> obtenerUltimoMovimiento(Cuenta cuenta);

    ArrayList<Movimiento> obtenerMovimientosPorFecha(Date fecha);

    Optional<Movimiento> obtenerMovimientoPorId(long id);

    boolean eliminarMovimientoPorId(long id);
}
