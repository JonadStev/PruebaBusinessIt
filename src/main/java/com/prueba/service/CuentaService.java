package com.prueba.service;

import com.prueba.dto.CuentaDTO;
import com.prueba.model.Cuenta;

import java.util.ArrayList;
import java.util.Optional;

public interface CuentaService {

    Cuenta guardarCuenta(CuentaDTO cuentaDTO);
    ArrayList<Cuenta> obtenerCuentas();

    Optional<Cuenta> obtenerCuentaPorId(long id);

    boolean eliminarCuentaPorId(long id);
}
