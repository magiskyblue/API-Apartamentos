# Sistema de Gestión de Alquileres y Apartamentos (API REST + Web MVC)

## 1. Descripción de la API
API RESTful y panel web administrativo desarrollado con **Spring Boot** para la gestión integral de arrendamiento de propiedades y unidades rentables. El sistema permite administrar el ciclo completo de alquiler: registro de clientes y propietarios, catálogo de propiedades e imágenes, desglose por unidades rentables, control de calendarios de disponibilidad, reservaciones con validación automática de solapamiento de fechas y cálculo de importes, procesamiento de pagos (confirmación y reembolsos), reseñas de estancias y notificaciones entre usuarios.

---

## 2. Tecnologías Utilizadas
* **Lenguaje:** Java 17+
* **Framework Backend:** Spring Boot (Spring Web REST, Spring MVC)
* **Persistencia de Datos:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL 8.x
* **Motor de Plantillas (Frontend):** Thymeleaf + CSS3
* **Seguridad y Validaciones:** Jakarta Bean Validation (`jakarta.validation`) y `BCryptPasswordEncoder` (Spring Security Crypto) para encriptación de contraseñas
* **Pruebas Unitarias:** JUnit 5 y Mockito
* **Gestor de Dependencias y Construcción:** Maven (`mvnw`)

---

## 3. Variables de Entorno Necesarias
Para evitar exponer credenciales sensibles en el repositorio, la aplicación utiliza las siguientes variables de entorno configuradas en `application.properties`:

| Variable | Descripción | Valor de ejemplo |
| :--- | :--- | :--- |
| `DB_URL` | URL de conexión JDBC para MySQL | `jdbc:mysql://localhost:3306/apartamentos_db` |
| `DB_USERNAME` | Usuario de la base de datos MySQL | `root` |
| `DB_PASSWORD` | Contraseña del usuario de MySQL | `tu_password_seguro` |
| `SERVER_PORT` | Puerto HTTP donde se ejecutará la aplicación | `8080` |

---

## 4. Pasos de Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/magiskyblue/API-Apartamentos.git
   cd apartamentos
   ```

2. **Crear la base de datos en MySQL:**
   Ejecuta la siguiente sentencia en tu gestor de MySQL:
   ```sql
   CREATE DATABASE apartamentos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Configurar las variables de entorno en la terminal:**
   * **En Windows (PowerShell):**
     ```powershell
     $env:DB_URL="jdbc:mysql://localhost:3306/apartamentos_db"
     $env:DB_USERNAME="root"
     $env:DB_PASSWORD="tu_password"
     $env:SERVER_PORT="8080"
     ```
   * **En Linux / macOS:**
     ```bash
     export DB_URL="jdbc:mysql://localhost:3306/apartamentos_db"
     export DB_USERNAME="root"
     export DB_PASSWORD="tu_password"
     export DB_SERVER_PORT="8080"
     ```

4. **Compilar y ejecutar el proyecto con Maven Wrapper:**
   * **En Windows:**
     ```cmd
     mvnw.cmd spring-boot:run
     ```
   * **En Linux / macOS:**
     ```bash
     ./mvnw spring-boot:run
     ```

5. **Acceder a la aplicación:**
   * **Interfaz Web (Dashboard):** `http://localhost:8080/`
   * **URL Base de la API REST:** `http://localhost:8080/api`

---

## 5. Listado de Endpoints

### Clientes (`/api/clientes`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/clientes` | Obtiene la lista de todos los clientes |
| `GET` | `/api/clientes/{id}` | Obtiene el detalle de un cliente por su ID |
| `POST` | `/api/clientes` | Registra un nuevo cliente (`PROPIETARIO`, `CLIENTE`, `EMPRESA`) |
| `PUT` | `/api/clientes/{id}` | Actualiza la información de un cliente existente |
| `DELETE` | `/api/clientes/{id}` | Elimina un cliente por su ID |

### Propiedades (`/api/propiedades`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/propiedades` | Lista todas las propiedades registradas |
| `GET` | `/api/propiedades/{id}` | Obtiene el detalle de una propiedad por su ID |
| `GET` | `/api/propiedades/tipo/{tipo}` | Filtra las propiedades por tipo (ej. `Apartamento`, `Casa`) |
| `GET` | `/api/propiedades/propietario/{idPropietario}` | Lista las propiedades de un propietario específico |
| `POST` | `/api/propiedades` | Crea una propiedad (valida que el cliente sea `PROPIETARIO` y esté `ACTIVO`) |
| `PUT` | `/api/propiedades/{id}` | Actualiza los datos de una propiedad |
| `DELETE` | `/api/propiedades/{id}` | Elimina una propiedad por su ID |

### Unidades Rentables (`/api/unidades`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/unidades` | Lista todas las unidades rentables |
| `GET` | `/api/unidades/{id}` | Obtiene una unidad rentable por su ID |
| `GET` | `/api/unidades/propiedad/{idPropiedad}` | Lista las unidades asociadas a una propiedad |
| `POST` | `/api/unidades` | Crea una unidad rentable (restringido si la propiedad está en `MANTENIMIENTO`) |
| `PUT` | `/api/unidades/{id}` | Actualiza una unidad rentable |
| `DELETE` | `/api/unidades/{id}` | Elimina una unidad rentable |

### Imágenes de Propiedad (`/api/propiedad-imagenes`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/propiedad-imagenes` | Lista todas las imágenes registradas |
| `GET` | `/api/propiedad-imagenes/{id}` | Obtiene una imagen por su ID |
| `GET` | `/api/propiedad-imagenes/propiedad/{idPropiedad}` | Obtiene todas las imágenes de una propiedad |
| `POST` | `/api/propiedad-imagenes` | Agrega una imagen a una propiedad |
| `PUT` | `/api/propiedad-imagenes/{id}` | Actualiza la URL o descripción de una imagen |
| `DELETE` | `/api/propiedad-imagenes/{id}` | Elimina una imagen por su ID |

### Disponibilidades (`/api/disponibilidades`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/disponibilidades` | Lista todos los registros de disponibilidad |
| `GET` | `/api/disponibilidades/{id}` | Consulta una disponibilidad por su ID |
| `GET` | `/api/disponibilidades/unidad/{idUnidad}` | Consulta los periodos de disponibilidad de una unidad |
| `POST` | `/api/disponibilidades` | Registra un bloque de disponibilidad o tarifa especial |
| `PUT` | `/api/disponibilidades/{id}` | Actualiza fechas, estado o precio especial de disponibilidad |
| `DELETE` | `/api/disponibilidades/{id}` | Elimina un registro de disponibilidad |

### Reservas (`/api/reservas`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/reservas` | Lista todas las reservaciones |
| `GET` | `/api/reservas/{id}` | Consulta una reservación por su ID |
| `POST` | `/api/reservas` | Crea una reservación validando disponibilidad y calculando el importe |
| `PUT` | `/api/reservas/{id}` | Actualiza fechas, check-in/check-out, estado o comentario |
| `DELETE` | `/api/reservas/{id}` | Elimina una reservación por su ID |

### Pagos (`/api/pagos`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/pagos` | Lista todos los pagos |
| `GET` | `/api/pagos/{id}` | Obtiene el detalle de un pago por su ID |
| `GET` | `/api/pagos/reserva/{idReserva}` | Obtiene todos los pagos asociados a una reservación |
| `POST` | `/api/pagos` | Crea un pago (`PENDIENTE` o `EN_PROCESO`) verificando el saldo de la reserva |
| `PUT` | `/api/pagos/{id}` | Modifica importe, fecha de vencimiento o método de pago |
| `POST` | `/api/pagos/{id}/confirmar` | Confirma un pago pendiente y cambia su estado a `PAGADO` |
| `POST` | `/api/pagos/{id}/reembolsar` | Reembolsa un pago confirmado y cambia su estado a `REEMBOLSADO` |
| `DELETE` | `/api/pagos/{id}` | Elimina un pago (bloqueado para pagos confirmados o reembolsados) |

### Reseñas (`/api/resenas`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/resenas` | Lista todas las reseñas |
| `GET` | `/api/resenas/{id}` | Obtiene una reseña por su ID |
| `GET` | `/api/resenas/reserva/{idReserva}` | Lista las reseñas asociadas a una reservación |
| `POST` | `/api/resenas` | Crea una nueva reseña con calificaciones de 1 a 5 |
| `PUT` | `/api/resenas/{id}` | Actualiza las calificaciones y el comentario de una reseña |
| `DELETE` | `/api/resenas/{id}` | Elimina una reseña por su ID |

### Notificaciones (`/api/notificaciones`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/notificaciones` | Lista todas las notificaciones |
| `GET` | `/api/notificaciones/{id}` | Obtiene una notificación por su ID |
| `GET` | `/api/notificaciones/destinatario/{idCliente}` | Obtiene las notificaciones recibidas por un cliente |
| `POST` | `/api/notificaciones` | Envía una nueva notificación entre clientes |
| `PUT` | `/api/notificaciones/{id}` | Actualiza asunto, mensaje o estado (`NO_LEIDA` / `LEIDA`) |
| `DELETE` | `/api/notificaciones/{id}` | Elimina una notificación por su ID |

---

## 6. Ejemplos de Peticiones y Respuestas

### Ejemplo 1: Crear un Cliente (`POST /api/clientes`)
**Request Body:**
```json
{
  "tipo": "PROPIETARIO",
  "nombres": "Elena",
  "apellidos": "Martínez",
  "email": "elena.martinez@correo.com",
  "telefono": "2291234567",
  "fechaNacimiento": "1990-05-14",
  "ine": "MARE900514HVR",
  "direccion": "Av. Colón 450, Veracruz",
  "estatus": "ACTIVO",
  "password": "MiPasswordSeguro123"
}
```

**Response (`201 Created`):**
```json
{
  "id": 1,
  "tipo": "PROPIETARIO",
  "nombres": "Elena",
  "apellidos": "Martínez",
  "email": "elena.martinez@correo.com",
  "telefono": "2291234567",
  "fechaNacimiento": "1990-05-14",
  "ine": "MARE900514HVR",
  "direccion": "Av. Colón 450, Veracruz",
  "fechaRegistro": "2026-10-13T21:15:00",
  "estatus": "ACTIVO"
}
```

### Ejemplo 2: Crear una Propiedad (`POST /api/propiedades`)
**Request Body:**
```json
{
  "propietario": { "id": 1 },
  "tipo": "Apartamento",
  "titulo": "Departamento Vista al Mar",
  "descripcion": "Amplio departamento frente al bulevar con acceso a alberca.",
  "direccion": "Blvd. Manuel Ávila Camacho 120",
  "ciudad": "Veracruz",
  "estadoFederativo": "Veracruz",
  "codigoPostal": "91900",
  "pais": "México",
  "latitud": 19.1738,
  "longitud": -96.1342,
  "precioNoche": 1500.00,
  "capacidad": 4,
  "numHabitaciones": 2,
  "numBanos": 2,
  "metrosCuadrados": 85,
  "comodidades": "Wi-Fi, Aire acondicionado, Estacionamiento",
  "reglas": "No mascotas, No fumar",
  "estado": "DISPONIBLE"
}
```

**Response (`201 Created`):**
```json
{
  "idPropiedad": 1,
  "propietario": {
    "id": 1,
    "tipo": "PROPIETARIO",
    "nombres": "Elena",
    "apellidos": "Martínez",
    "email": "elena.martinez@correo.com",
    "estatus": "ACTIVO"
  },
  "tipo": "Apartamento",
  "titulo": "Departamento Vista al Mar",
  "descripcion": "Amplio departamento frente al bulevar con acceso a alberca.",
  "direccion": "Blvd. Manuel Ávila Camacho 120",
  "ciudad": "Veracruz",
  "estadoFederativo": "Veracruz",
  "codigoPostal": "91900",
  "pais": "México",
  "latitud": 19.1738,
  "longitud": -96.1342,
  "precioNoche": 1500.00,
  "capacidad": 4,
  "numHabitaciones": 2,
  "numBanos": 2,
  "metrosCuadrados": 85,
  "comodidades": "Wi-Fi, Aire acondicionado, Estacionamiento",
  "reglas": "No mascotas, No fumar",
  "estado": "DISPONIBLE",
  "fechaRegistro": "2026-10-13T21:18:00",
  "fechaActualizacion": "2026-10-13T21:18:00"
}
```

### Ejemplo 3: Crear una Reserva (`POST /api/reservas`)
**Request Body:**
```json
{
  "unidad": { "idUnidad": 1 },
  "cliente": { "id": 2 },
  "fechaEntrada": "2026-11-01T15:00:00",
  "fechaSalida": "2026-11-04T11:00:00",
  "estado": "CONFIRMADA",
  "comentario": "Llegada por la tarde"
}
```

**Response (`201 Created`):**
```json
{
  "idReservacion": 1,
  "unidad": {
    "idUnidad": 1,
    "nombre": "Unidad 101",
    "tipo": "Suite",
    "capacidad": 2,
    "precioNoche": 1000.00,
    "estado": "DISPONIBLE"
  },
  "cliente": {
    "id": 2,
    "tipo": "CLIENTE",
    "nombres": "Carlos",
    "apellidos": "Galicia",
    "email": "