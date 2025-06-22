package com.cfgj.validacion.validacionyverificacionfinal.calculadores;

import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.cfgj.validacion.validacionyverificacionfinal.modelo.*;

public class RegistrarPrestamoAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        Long miembroId = (Long) getView().getValue("miembro.id");
        Long libroId = (Long) getView().getValue("libro.id");

        if (miembroId == null || libroId == null) {
            addError("Debe seleccionar un miembro y un libro.");
            return;
        }

        EntityManager em = XPersistence.getManager();
        Miembro miembro = em.find(Miembro.class, miembroId);
        Libro libro = em.find(Libro.class, libroId);

        if (tieneMultasPendientes(miembro)) {
            addError("El miembro tiene multas sin pagar. No se puede registrar el préstamo.");
            return;
        }

        Prestamo p = new Prestamo();
        p.setLibro(libro);
        p.setMiembro(miembro);
        p.setFechaPrestamo(new Date());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // préstamo por 7 días
        p.setFechaDevolucionEsperada(cal.getTime());

        p.setDevuelto(false);
        p.setMulta(null);

        em.persist(p);
        addMessage("Préstamo registrado correctamente.");
        getView().reset();
    }

    private boolean tieneMultasPendientes(Miembro miembro) {
        EntityManager em = XPersistence.getManager();
        String jpql = "SELECT COUNT(p) FROM Prestamo p WHERE p.miembro = :miembro AND p.multa > 0 AND p.devuelto = true";
        Long count = em.createQuery(jpql, Long.class)
                       .setParameter("miembro", miembro)
                       .getSingleResult();
        return count > 0;
    }
}
