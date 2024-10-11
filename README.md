# API de Candidatos

Este proyecto es una API REST desarrollada con **Spring Boot**, diseñada para gestionar candidatos en un proceso de reclutamiento. Se implementa una **arquitectura hexagonal** para asegurar una separación entre el dominio del negocio, la infraestructura y la lógica de la aplicación.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.4**
- **Spring Security** (para seguridad)
- **JWT (JSON Web Token)** (para autenticación)
- **Flyway** (para ejecutar los scripts)
- **Spring Data JPA**
- **MySQL Database**
- **H2 Database** (para pruebas en memoria)
- **Swagger/OpenAPI** (para documentación de API)
- **JUnit 5 y Mockito** (para pruebas)
- **AWS - RDS**
- **RENDER - NUBE**

## Estructura del Proyecto

El proyecto sigue el patrón de arquitectura hexagonal, dividiendo las responsabilidades en capas bien definidas:

```bash
src/
├── main/
│   ├── java/com/seek/candidates/
│   │   ├── application/        # Lógica de negocio y casos de uso
│   │   ├── domain/             # Entidad del dominio
│   │   ├── infrastructure/     # Controller, dto, Exception, Repositorio, security, configuración
│   └── resources/
│       ├── application.yml     # Configuración de la aplicación
│       ├── db/migration        # scripts de migración
├── test/                       # Pruebas unitarias e integración
```

## Configuración de la Aplicación

El archivo de configuración principal es `application.yml` en el directorio `src/main/resources/`, donde se configuran propiedades como la base de datos, seguridad y otros parámetros de la aplicación.

```yaml
spring:
  application:
    name: ms-candidate
  datasource:
    url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:candidates_db}?createDatabaseIfNotExist=true&useSSL=false
    username: ${DB_USER:root}
    password: ${DB_PASSWORD:}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  flyway:
    enabled: true
    baseline-on-migrate: true
    placeholder-replacement: true
    locations: classpath:db/migration
```
## Endpoints Principales de la API

La API REST permite la gestión de candidatos mediante los siguientes endpoints:

| Método  | Endpoint                     | Descripción                          |
|---------|------------------------------|--------------------------------------|
| GET     | `/api/candidates`             | Obtener la lista de candidatos       |
| GET     | `/api/candidates/{id}`        | Obtener un candidato por su ID       |
| POST    | `/api/candidates`             | Crear un nuevo candidato             |
| PUT     | `/api/candidates/{id}`        | Actualizar un candidato existente    |
| DELETE  | `/api/candidates/{id}`        | Eliminar un candidato                |

## Autenticación con JWT

Para proteger los endpoints, se implementa JWT (JSON Web Token). Un usuario debe autenticarse mediante el endpoint `/api/authenticate/login` para obtener un token JWT, que deberá incluir en las cabeceras de las solicitudes posteriores.

### Ejemplo de autenticación:

```bash
POST /api/authenticate/login
{
  "username": "admin",
  "password": "123"
}
```
### La respuesta contendrá un token JWT:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
### Uso del token:

En cada solicitud a un endpoint protegido, el token debe ser incluido en la cabecera `Authorization`:

```bash
Authorization: Bearer <your-token>
```

## Despliegue de la Aplicación en la Nube

1. **Dockerfile**
    Este archivo `Dockerfile` se utiliza para construir la imagen del contenedor de la aplicación Spring Boot. A continuación se detallan los pasos para su construcción:

    ```dockerfile
    # Etapa de construcción utilizando Maven
    FROM maven:3.8.5-openjdk-17-slim AS build
    COPY . .
    RUN mvn clean package -DskipTests

    # Etapa de ejecución utilizando OpenJDK
    FROM openjdk:17.0.1-jdk-slim
    COPY --from=build /target/candidates-0.0.1-SNAPSHOT.jar app.jar
    EXPOSE 8080
    ENTRYPOINT [ "java", "-jar", "app.jar" ]
    ```

2. **Subida de la Imagen a Docker Hub**

   Una vez que la imagen del contenedor se ha construido localmente utilizando el `Dockerfile`, se puede subir a Docker Hub para facilitar su distribución y despliegue. Para hacerlo, sigue estos pasos:

   ```bash
   # Iniciar sesión en Docker Hub
   docker login

   # Etiquetar la imagen (asegúrate de reemplazar <your-dockerhub-username> con tu nombre de usuario)
   docker tag candidates-0.0.1-SNAPSHOT <your-dockerhub-username>/candidates:latest

   # Subir la imagen a Docker Hub
   docker push <your-dockerhub-username>/candidates:latest

3. **Despliegue de la Imagen en Render**

    Una vez que la imagen está disponible en Docker Hub, puedes desplegarla en Render. Render es una plataforma de nube que permite desplegar aplicaciones fácilmente.

## Documentación con Swagger

La API está documentada utilizando Swagger. Puedes acceder a la documentación interactiva desde:

```bash
https://ms-candidates-latest.onrender.com/swagger-ui.html
```

## Postman Collection

El archivo de colección de Postman con ejemplos de llamadas a las APIs se puede encontrar en la raiz del proyecto. Puedes importarlo en Postman y ejecutar los casos de prueba predefinidos.

1. Importa el archivo `RESTful_API_candidates.postman_collection.json` en Postman.
2. Ejecuta las solicitudes para probar los diferentes endpoints.


