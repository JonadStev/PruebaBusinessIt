package com.prueba.repository;

import com.prueba.model.Cuenta;
import com.prueba.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    abstract ArrayList<Movimiento> findByCuentaOrderByCuentaDesc(Cuenta cuenta);
    abstract ArrayList<Movimiento> findByFecha(Date fecha);

    ArrayList<Movimiento> findByFechaBetween(Date des,Date has);
}
