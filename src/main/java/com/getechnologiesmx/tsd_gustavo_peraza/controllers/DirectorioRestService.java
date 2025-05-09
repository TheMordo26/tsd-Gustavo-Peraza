package com.getechnologiesmx.tsd_gustavo_peraza.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.getechnologiesmx.tsd_gustavo_peraza.models.Persona;
import com.getechnologiesmx.tsd_gustavo_peraza.services.Directorio;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/directorio")
public class DirectorioRestService {

    @Autowired
    private Directorio directorio;

    private static final Logger logger = LoggerFactory.getLogger(DirectorioRestService.class);

    @GetMapping("/persona/identificacion/{identificacion}")
    public ResponseEntity<?> getPersonaByIdentificacion(@PathVariable String identificacion) {
        logger.info("Buscando persona con identificación: {}", identificacion);
        try {
            Persona persona = directorio.findPersonaByIdentificacion(identificacion);
            logger.info("Persona encontrada: {}", persona);
            return ResponseEntity.ok(persona);
        } catch (RuntimeException e) {
            logger.error("Error al buscar persona: {}", e.getMessage());
            Map<String,String> body = Collections.singletonMap(
            "error", e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
}

    @GetMapping("/personas")
    public ResponseEntity<?> getAllPersonas() {
        logger.info("Obteniendo lista de todas las personas");
        try {
            List<Persona> lista = directorio.findPersonas();
            logger.info("Se encontraron {} personas", lista.size());
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            logger.error("Error al obtener personas: {}", e.getMessage());
            Map<String,String> body = Collections.singletonMap(
                "error", e.getMessage()
            );
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
        }
    }

    @DeleteMapping("/persona/{identificacion}")
    public ResponseEntity<Map<String,String>> deletePersona(@PathVariable String identificacion) {
        logger.info("Eliminando persona con identificación: {}", identificacion);
        try {
            directorio.deletePersonaByIdentificacion(identificacion);
            logger.info("Persona eliminada correctamente: {}", identificacion);
            return ResponseEntity.ok(
                Collections.singletonMap("message", 
                "Persona con identificación '" + identificacion + "' eliminada correctamente"
            )
            );
        } catch (RuntimeException e) {
            logger.error("Error al eliminar persona: {}", e.getMessage());
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/persona")
    public ResponseEntity<?> storePersona(@RequestBody @Valid Persona persona, BindingResult result) {
        logger.info("Intentando guardar persona: {}", persona); 
        if (result.hasErrors()) {
            logger.warn("Validación fallida para persona: {}", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Algunos campos son inválidos o están vacíos."));
        }
        try {
            Persona stored = directorio.storePersona(persona);
            logger.info("Persona guardada exitosamente con ID: {}", stored.getIdPersona());
            return ResponseEntity.ok(stored);
        } catch (RuntimeException e) {
            logger.error("Error al guardar persona: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
