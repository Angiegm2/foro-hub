# Foro Hub

Foro Hub es una API REST creada con Spring que gestiona los tópicos de un foro, permitiendo realizar las operaciones **CRUD** (Create, Read, Update, Delete). Este proyecto sigue las mejores prácticas del modelo REST y busca implementar funcionalidades clave que aseguren su robustez y seguridad.

---

## Características principales

- **Crear un nuevo tópico**: Permite a los usuarios agregar nuevos tópicos al foro.
- **Mostrar todos los tópicos creados**: Recupera una lista de todos los tópicos disponibles en la base de datos.
- **Mostrar un tópico específico**: Proporciona detalles de un tópico seleccionado.
- **Actualizar un tópico**: Permite modificar la información de un tópico existente.
- **Eliminar un tópico**: Permite remover tópicos de la base de datos.

---

## Tecnologías utilizadas

- **Spring Framework**: Para la creación y gestión de la API REST.
- **MySQL**: Base de datos para la persistencia de la información.
- **Java**: Lenguaje de programación principal.
- **Maven**: Herramienta para la gestión de dependencias.
- **Spring Security**: Para la implementación de autenticación y autorización.

---

## Funcionalidades clave

### Modelo REST
- Implementación de rutas según las mejores prácticas REST.

### Validaciones
- Validaciones según las reglas de negocio para asegurar la calidad de los datos ingresados.

### Persistencia de datos
- Uso de una base de datos relacional para guardar y gestionar los tópicos.

### Seguridad
- Autenticación y autorización para restringir el acceso a la información.

---

## Instalación y configuración

1. Clona este repositorio:
   ```bash
   git clone <url-del-repositorio>
2. Importa el proyecto en tu IDE favorito (IntelliJ IDEA recomendado).

3. Configura las variables de entorno necesarias:
   - DB_NAME: Nombre de la base de datos.
   - DB_USER: Usuario de la base de datos.
   - DB_PASSWORD: Contraseña del usuario.

4. Ejecuta el proyecto con Maven:
    ```bash
    mvn spring-boot:run

Accede a la API en: http://localhost:8080.

## Documentación de la API
### Rutas principales
`-`POST /topics: Crea un nuevo tópico.
`-`GET /topics: Recupera todos los tópicos.
`-`GET /topics/{id}: Recupera un tópico específico por ID.
`-`PUT /topics/{id}: Actualiza un tópico existente.
`-`DELETE /topics/{id}: Elimina un tópico por ID.

## Futuras mejoras
`-`Implementación de páginas y filtros para los tópicos.
`-`Integración con herramientas de monitoreo.
`-`Mejora del sistema de autenticación con OAuth2.

## Contribuciones
Las contribuciones son bienvenidas. Si encuentras algún error o deseas agregar nuevas funcionalidades, por favor abre un `**issue**` o envía un `**pull request**`.

## Autor
[GitHub Profile](https://github.com/Angiegm2)


Este proyecto es parte de un aprendizaje continuo en el desarrollo de aplicaciones con Spring Framework.



