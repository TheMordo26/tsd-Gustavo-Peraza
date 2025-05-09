package com.getechnologiesmx.tsd_gustavo_peraza.services;

import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.getechnologiesmx.tsd_gustavo_peraza.models.Persona;
import com.getechnologiesmx.tsd_gustavo_peraza.repositories.PersonaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Service
public class Directorio {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PersonaRepository personaRepository;

    public Persona findPersonaByIdentificacion(String identificacion) {
        try {
            return em.createQuery(
                    "SELECT p FROM Persona p WHERE LOWER(p.identificacion) = LOWER(:identificacion)", 
                    Persona.class
                )
                .setParameter("identificacion", identificacion)
                .getSingleResult();
        } catch (NoResultException ex) {
            throw new RuntimeException(
                "No se encontró a la persona con identificación: " + identificacion
            );
        }
    }

    public List<Persona> findPersonas() {
        List<Persona> personas = personaRepository.findAll();
        if (personas.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna persona registrada");
        }
        return personas;
    }
    
    public void deletePersonaByIdentificacion(String identificacion) {
        Persona persona = findPersonaByIdentificacion(identificacion);
        if (persona != null){
            personaRepository.delete(persona);
        }
    }
    
    public Persona storePersona(Persona persona) {
        if (persona.getIdentificacion() == null || persona.getIdentificacion().trim().isEmpty()) {
            throw new RuntimeException("La identificación no puede estar vacía.");
        } if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacio.");
          } if (persona.getApellidoPaterno() == null || persona.getApellidoPaterno().trim().isEmpty()) {
            throw new RuntimeException("El apellido paterno no puede estar vacio.");
          }

        String nuevaIdentificacion = persona.getIdentificacion().trim().toLowerCase();

        boolean exists = personaRepository.findAll().stream()
            .anyMatch(p -> p.getIdentificacion() != null 
                && p.getIdentificacion().trim().toLowerCase().equals(nuevaIdentificacion)
            );

        if (exists) {
            throw new RuntimeException(
                "Ya existe una persona con la identificación: " + persona.getIdentificacion().trim()
            );
        }

        return personaRepository.save(persona);
    }

}
