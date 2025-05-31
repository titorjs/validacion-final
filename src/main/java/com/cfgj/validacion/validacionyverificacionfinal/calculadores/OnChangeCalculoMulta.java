package com.cfgj.validacion.validacionyverificacionfinal.calculadores;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.cfgj.validacion.validacionyverificacionfinal.modelo.*;

public class OnChangeCalculoMulta extends OnChangePropertyBaseAction {

    @Override
    public void execute() throws Exception {
        Map<String, Object> viewValues = getView().getValues();
        
        Date fechaEsperada = (Date) viewValues.get("fechaDevolucionEsperada");
        Date fechaReal = (Date) getNewValue();

        BigDecimal multa = BigDecimal.ZERO;

        if (fechaEsperada != null && fechaReal != null) {
            long diasRetraso = ChronoUnit.DAYS.between(
                fechaEsperada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                fechaReal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );

            if (diasRetraso > 0) {
                multa = obtenerMultaPorDia().multiply(BigDecimal.valueOf(diasRetraso));
            }
        }

        getView().setValue("multa", multa);
    }

    private BigDecimal obtenerMultaPorDia() {
        EntityManager em = XPersistence.getManager(); // Esta es la forma correcta
        ParametroSistema param = em.find(ParametroSistema.class, "VALOR_MULTA_DIA");
        return (param != null && param.getValorNumerico() != null) ? param.getValorNumerico() : BigDecimal.ZERO;
    }
}
