package com.getechnologiesmx.tsd_gustavo_peraza.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.getechnologiesmx.tsd_gustavo_peraza.models.Factura;
import com.getechnologiesmx.tsd_gustavo_peraza.services.Ventas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/factura")
public class FacturaRestService {

    @Autowired
    private Ventas ventas;

    private static final Logger logger = LoggerFactory.getLogger(FacturaRestService.class);

    @GetMapping("/persona/{identificacion}")
    public ResponseEntity<?> getFacturasByIdentificacion(@PathVariable String identificacion) {
        logger.info("Buscando facturas para la identificación: {}", identificacion);
        try {
            List<Factura> facturas = ventas.findFacturaByPersona(identificacion);
            if (facturas.isEmpty()) {
                logger.warn("No se encontraron facturas para la identificación: {}", identificacion);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "No se encontraron facturas para la identificación: " + identificacion));
            }
            logger.info("Se encontraron {} factura(s) para la identificación: {}", facturas.size(), identificacion);
            return ResponseEntity.ok(facturas);
        } catch (RuntimeException e) {
            logger.error("Error al buscar facturas para identificación {}: {}", identificacion, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("error", "No se encontró a la persona con identificación: " + identificacion));
        }
    }

    @PostMapping
    public ResponseEntity<?> storeFactura(@RequestBody Factura factura) {
        logger.info("Recibiendo solicitud: {}", factura);
        try {
            Factura nuevaFactura = ventas.storeFactura(factura);
            logger.info("Factura guardada con ID: {}", nuevaFactura.getIdFactura());
            return ResponseEntity.ok(nuevaFactura);
        } catch (RuntimeException e) {
            logger.error("Error al guardar la factura: {}", e.getMessage());
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Collections.singletonMap("error", e.getMessage()));
        }
    }


}
