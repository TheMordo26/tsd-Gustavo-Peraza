package com.getechnologiesmx.tsd_gustavo_peraza.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.getechnologiesmx.tsd_gustavo_peraza.models.Factura;
import com.getechnologiesmx.tsd_gustavo_peraza.models.Persona;
import com.getechnologiesmx.tsd_gustavo_peraza.repositories.FacturaRepository;

@Service
public class Ventas {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private Directorio directorio;

    public List<Factura> findFacturaByPersona(String identificacion) {
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);
        return facturaRepository.findAll().stream()
            .filter(f -> f.getPersona().equals(persona))
            .toList();
    }

    public Factura storeFactura(Factura factura) {
        if (factura.getPersona() == null || factura.getPersona().getIdentificacion() == null) {
            throw new RuntimeException("La factura debe incluir una persona con identificación.");
        } if (factura.getFecha() == null) {
            throw new RuntimeException("La fecha no puede estar vacía.");
        } if (factura.getMonto() == null || factura.getMonto() <= 0.0) {
            throw new RuntimeException("El Monto no puede estar vacio o ser menor o igual a 0.");
          }

        Persona persona = directorio.findPersonaByIdentificacion(factura.getPersona().getIdentificacion());
        factura.setPersona(persona);

        return facturaRepository.save(factura);
    }
}
