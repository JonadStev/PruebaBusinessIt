package com.prueba.service;

import com.prueba.dto.ConsultarMovimientos;
import com.prueba.dto.MovimientoDTO;
import com.prueba.emun.TipoMovimiento;
import com.prueba.model.Cuenta;
import com.prueba.model.Movimiento;
import com.prueba.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaService cuentaService;

    @Value("${limite.retiro}")
    private double limiteRetiro;

    @Override
    public String guardarMovimiento(MovimientoDTO movimientoDTO) {

        Optional<Cuenta> cuentaOp = cuentaService.obtenerCuentaPorId(movimientoDTO.getIdCuenta());
        String tipoMov = movimientoDTO.getTipoMovimiento();
        Optional<Movimiento> ultimoMovimiento = obtenerUltimoMovimiento(cuentaOp.get());
        String mensaje = "";
        Movimiento movimiento = null;
        double valor = movimientoDTO.getValor();
        //Date date = movimientoDTO.getFecha();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String dateInString = movimientoDTO.getFecha();
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (ultimoMovimiento.isPresent()){
            // Si tiene movimientos previos, tomamos el ultimo saldo registrado
            if(tipoMov.equalsIgnoreCase("debito")){
                if(ultimoMovimiento.get().getSaldo() == 0)
                    mensaje = "Saldo no disponible";
                else if (ultimoMovimiento.get().getSaldo() < valor)
                    mensaje = "Saldo no disponible";
                else{

                    AtomicReference<Double> totalDebitoDiario = new AtomicReference<>((double) 0);
                    obtenerMovimientosPorFecha(date).stream().filter(x -> x.getTipoMovimiento().equals(TipoMovimiento.DEBITO)).forEach(x -> totalDebitoDiario.updateAndGet(v -> new Double((double) (v + x.getValor()))));

                    if(totalDebitoDiario.get() == limiteRetiro)
                        mensaje = "Cupo diario Excedido";
                    else if (totalDebitoDiario.get() + valor > limiteRetiro) {
                        mensaje = "Cupo diario Excedido";
                    }else{
                        movimiento = new Movimiento(date,TipoMovimiento.DEBITO,valor,ultimoMovimiento.get().getSaldo()-valor,cuentaOp.get());
                        mensaje = "Debito realizado con exito";
                    }

                }
            }else{
                movimiento = new Movimiento(date,TipoMovimiento.CREDITO,valor,ultimoMovimiento.get().getSaldo()+valor,cuentaOp.get());
                mensaje = "Credito realizado con exito";
            }
        }else {
            // No tiene movimientos, primera vez creando un movimiento se toma el saldo inicial de la cuenta
            if(tipoMov.equalsIgnoreCase("debito")){
                movimiento = new Movimiento(date,TipoMovimiento.DEBITO,valor,cuentaOp.get().getSaldoInicial()-valor,cuentaOp.get());
                mensaje = "Debito realizado con exito";
            }else {
                movimiento = new Movimiento(date,TipoMovimiento.CREDITO,valor,cuentaOp.get().getSaldoInicial()+valor,cuentaOp.get());
                mensaje = "Credito realizado con exito";
            }
        }
        if(mensaje.equals("Debito realizado con exito") || mensaje.equals("Credito realizado con exito"))
            movimientoRepository.save(movimiento);

        return mensaje;
    }

    @Override
    public ArrayList<Movimiento> obtenerMovimientos() {
        return (ArrayList<Movimiento>) movimientoRepository.findAll();
    }

    //Para la seccion de reportes
    @Override
    public List<Movimiento> obtenerMovimientosPorClienteYFecha(ConsultarMovimientos consultarMovimientos) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechaInicio = consultarMovimientos.getFechaInicio();
        String fechaFinal = consultarMovimientos.getFechaFinal();
        Date dInicio = null;
        Date dFinal = null;
        try {
            dInicio = formatter.parse(fechaInicio);
            dFinal = formatter.parse(fechaFinal);
            List<Movimiento> movimientosFilter = movimientoRepository.findByFechaBetween(dInicio,dFinal)
                    .stream().filter(x -> x.getCuenta().getCliente().getId() == consultarMovimientos.getIdCliente())
                    .collect(Collectors.toList());
            return movimientosFilter;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Optional<Movimiento> obtenerUltimoMovimiento(Cuenta cuenta){
        try {
            ArrayList<Movimiento> lista = movimientoRepository.findByCuentaOrderByCuentaDesc(cuenta);
            List<Long> listaIdCuentas = new ArrayList<>();
            for (Movimiento movimiento: lista){
                listaIdCuentas.add(movimiento.getId());
            }
            long idUltimoMovimiento = Collections.max(listaIdCuentas);
            Optional<Movimiento> ultimoMovimiento = obtenerMovimientoPorId(idUltimoMovimiento);
            return ultimoMovimiento;
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public ArrayList<Movimiento> obtenerMovimientosPorFecha(Date fecha) {
        return movimientoRepository.findByFecha(fecha);
    }

    @Override
    public Optional<Movimiento> obtenerMovimientoPorId(long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public boolean eliminarMovimientoPorId(long id) {
        try {
            Optional<Movimiento> movimiento = obtenerMovimientoPorId(id);
            movimientoRepository.delete(movimiento.get());
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
