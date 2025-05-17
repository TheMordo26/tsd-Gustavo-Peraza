package com.getechnologiesmx.tsd_gustavo_peraza.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String home(){
        return """
            <h2>API de prueba tecnica</h2>
            <p>Endpoints disponibles:</p>
            <ul>
                <li><strong>POST</strong> /api/directorio/persona -  Crea una nueva persona (validaciones incluidas)</li>
                <li><strong>GET</strong> /api/directorio/personas - Lista todas las personas</li>
                <li><strong>GET</strong> /api/directorio/persona/identificacion/{identificacion} - Busca persona por identificación</li>
                <li><strong>DELETE</strong> /api/directorio/persona/{identificacion} - Elimina persona por identificación</li>
                <li><strong>POST</strong> /api/factura - Crea una nueva factura (requiere persona existente)</li>
                <li><strong>GET</strong> /api/factura/persona/{identificacion} - Lista facturas por persona</li>
            </ul>

            <h3>Ejemplo de JSON para crear una persona (POST /api/directorio/persona):</h3>
            <pre>
                {
                "nombre": "Gustavo Efren",
                "apellidoPaterno": "Peraza",
                "apellidoMaterno": "Polanco",
                "identificacion": "GEPP26"
                }
            </pre>

            <h3>Ejemplo de JSON para crear una factura (POST /api/factura):</h3>
            <pre>
                {
                "fecha": "2025-05-09",
                "monto": 1500.0,
                "persona": {
                    "identificacion": "GEPP26"
                }
                }
            </pre>
        """;
    }
}
