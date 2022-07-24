package com.prueba.service;

import com.prueba.dto.CuentaDTO;
import com.prueba.emun.TipoCuenta;
import com.prueba.model.Cliente;
import com.prueba.model.Cuenta;
import com.prueba.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class CuentaServiceImpl implements CuentaService{

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    ClienteService clienteService;

    @Override
    public Cuenta guardarCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuentaCliente = null;
        try {
            Optional<Cliente> cliente = clienteService.obtenerClientePorId(cuentaDTO.getIdCliente());
            String tipoCuenta = cuentaDTO.getTipoCuenta();
            Optional<Cuenta> cuentaFound = obtenerCuentaPorId(cuentaDTO.getId());
            if(cuentaFound.isEmpty()){
                cuentaCliente = new Cuenta(cuentaDTO.getNumeroCuenta(),
                        (tipoCuenta.equalsIgnoreCase("ahorros"))? TipoCuenta.AHORROS:TipoCuenta.CORRIENTE,
                        cuentaDTO.getSaldoInicial(),
                        cuentaDTO.getEstado(),
                        cliente.get());
            }else {
                cuentaCliente = cuentaFound.get();
                cuentaCliente.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
                TipoCuenta tipoCuenta1 = (tipoCuenta.equalsIgnoreCase("ahorros"))? TipoCuenta.AHORROS:TipoCuenta.CORRIENTE;
                cuentaCliente.setTipoCuenta(tipoCuenta1);
                cuentaCliente.setSaldoInicial(cuentaDTO.getSaldoInicial());
                cuentaCliente.setEstado(cuentaDTO.getEstado());
                cuentaCliente.setCliente(cliente.get());
            }
            return cuentaRepository.save(cuentaCliente);
        }catch (Exception e){
            return null;
        }


    }

    @Override
    public ArrayList<Cuenta> obtenerCuentas() {
        return (ArrayList<Cuenta>) cuentaRepository.findAll();
    }

    @Override
    public Optional<Cuenta> obtenerCuentaPorId(long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public boolean eliminarCuentaPorId(long id) {
        try {
            Optional<Cuenta> cuenta = obtenerCuentaPorId(id);
            cuentaRepository.delete(cuenta.get());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
