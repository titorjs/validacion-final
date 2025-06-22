package com.cfgj.validacion.validacionyverificacionfinal.calculadores;

import java.math.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.cfgj.validacion.validacionyverificacionfinal.modelo.*;

public class CobrarMultaAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        Long miembroId = (Long) getView().getValue("miembro.id");
        if (miembroId == null) {
            addError("Seleccione un miembro.");
            return;
        }

        EntityManager em = XPersistence.getManager();
        Miembro miembro = em.find(Miembro.class, miembroId);

        String jpql = "SELECT p FROM Prestamo p WHERE p.miembro = :miembro AND p.devuelto = true";
        List<Prestamo> prestamos = em.createQuery(jpql, Prestamo.class)
                                     .setParameter("miembro", miembro)
                                     .getResultList();

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal multaPorDia = obtenerMultaPorDia();

        for (Prestamo p : prestamos) {
            if (p.getFechaDevolucionEsperada() != null && p.getFechaDevolucionReal() != null) {
                long diasRetraso = ChronoUnit.DAYS.between(
                    p.getFechaDevolucionEsperada().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    p.getFechaDevolucionReal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                );

                if (diasRetraso > 0) {
                    BigDecimal multa = multaPorDia.multiply(BigDecimal.valueOf(diasRetraso));
                    p.setMulta(multa);
                    total = total.add(multa);
                } else {
                    p.setMulta(BigDecimal.ZERO);
                }
            }
        }

        if (total.compareTo(BigDecimal.ZERO) == 0) {
            addMessage("El miembro no tiene multas pendientes.");
            return;
        }

        // Simulación del pago
        for (Prestamo p : prestamos) {
            if (p.getMulta() != null && p.getMulta().compareTo(BigDecimal.ZERO) > 0) {
                p.setMulta(BigDecimal.ZERO);
            }
        }

        addMessage("Multas cobradas exitosamente. Total pagado: $" + total);
        getView().reset();
    }

    private BigDecimal obtenerMultaPorDia() {
        EntityManager em = XPersistence.getManager();
        ParametroSistema param = em.find(ParametroSistema.class, "VALOR_MULTA_DIA");
        return (param != null && param.getValorNumerico() != null) ? param.getValorNumerico() : BigDecimal.ZERO;
    }
}
