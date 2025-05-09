package com.getechnologiesmx.tsd_gustavo_peraza.repositories;

import com.getechnologiesmx.tsd_gustavo_peraza.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
