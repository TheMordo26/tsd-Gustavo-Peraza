## TSD Gustavo Peraza

Esta es una prueba técnica realizada para la empresa GetechnologiesMx utilizando tecnologías Backend

## Tecnologías utilizadas

- Java 24
- Spring Boot 3.4.5
- Spring Data JPA
- Spring web
- H2 Database
- Maven

## Estructura del proyecto

- `/models` – Entidades JPA como `Persona` y `Factura`.
- `/services` – Lógica de negocio en clases como `Directorio` y `Ventas`.
- `/repositories` – Interfaces que extienden `JpaRepository` para acceso a base de datos (`PersonaRepository`, `FacturaRepository`).
- `/controllers` – Endpoints REST para personas y facturas.

## Endpoints principales

### Personas

- `POST /api/directorio/persona` – Crea una nueva persona (validaciones incluidas).

  **Ejemplo en POSTMAN:**
  ```json
  POST http://localhost:8080/api/directorio/persona
  Content-Type: application/json
  {
    "nombre": "Gustavo Efren",
    "apellidoPaterno": "Peraza",
    "apellidoMaterno": "Polanco",
    "identificacion": "GEPP26"
  }
- `GET /api/directorio/personas` – Lista todas las personas.

  **Ejemplo en POSTMAN:**
  `GET http://localhost:8080/api/directorio/personas`
- `GET /api/directorio/persona/identificacion/{identificacion}` – Busca persona por identificación.
  
  **Ejemplo en POSTMAN:**
  `GET http://localhost:8080/api/directorio/persona/identificacion/GEPP26`
- `DELETE /api/directorio/persona/{identificacion}` – Elimina persona por identificación.
  
  **Ejemplo en POSTMAN:**
  `DELETE http://localhost:8080/api/directorio/persona/GEPP26`

### Facturas

- `POST /api/factura` – Crea una nueva factura (requiere persona existente).

  **Ejemplo en POSTMAN:**
  ```json
  POST http://localhost:8080/api/factura
  Content-Type: application/json
  
  {
    "fecha": "2025-05-09",
    "monto": 1500.0,
    "persona": {
      "identificacion": "GEPP26"
    }
  }

- `GET /api/factura/persona/{identificacion}` – Lista facturas por persona.
  
  **Ejemplo en POSTMAN:**
  `GET http://localhost:8080/api/factura/persona/GEPP26`

## Cómo correr el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/TheMordo26/tsd-Gustavo-Peraza.git
