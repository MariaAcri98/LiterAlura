# LiterAlura: Desafío de Spring Boot y Java

## Descripción del Proyecto

*Este proyecto es una aplicación de consola en Java, desarrollada como parte del programa de formación Oracle ONE en colaboración con Alura Latam. Su propósito es interactuar con la API de Gutendex, una vasta colección de libros de dominio público, para permitir a los usuarios buscar, registrar y consultar datos sobre obras literarias y sus autores. La aplicación se conecta a una base de datos PostgreSQL para la persistencia de datos.
Este proyecto fue un desafío para aplicar conocimientos de:*

* Java: Conceptos fundamentales y programación orientada a objetos.

* Spring Boot: Uso del framework para crear aplicaciones robustas y modulares.

* Persistencia de Datos: Conexión y gestión de datos con Spring Data JPA y PostgreSQL.

* Consumo de API: Interacción con servicios web externos (Gutendex).

* Buenas Prácticas: Manejo de seguridad, inyección de dependencias y control de versiones con Git.

# Funcionalidades Clave

### La aplicación presenta un menú interactivo con las siguientes opciones:

1. Buscar libro por título: Realiza una búsqueda en la API de Gutendex y guarda el libro y su autor en la base de datos local si no existen.

2. Listar libros registrados: Muestra todos los libros guardados en la base de datos.

3. Listar autores: Muestra todos los autores únicos registrados.

4. Listar autores vivos en un año: Filtra autores que estaban vivos en un año específico.

5. Listar libros por idioma: Permite buscar libros por un idioma específico.

6. Mostrar estadísticas de descargas: Muestra el promedio, mínimo y máximo de descargas de los libros registrados.

7. Buscar autor por nombre: Busca autores en la base de datos local por una coincidencia en el nombre.

8. Top 10 libros más descargados: Muestra los 10 libros más populares según el número de descargas.

## Tecnologías Utilizadas

**Java 17: Lenguaje de programación.**

**Spring Boot 3.5.4: Framework para el desarrollo de la aplicación.**

**Spring Data JPA: Abstracción para la capa de persistencia.**

**Hibernate: Implementación de JPA para mapeo objeto-relacional.**

**PostgreSQL: Base de datos relacional.**

**Jackson: Biblioteca para el procesamiento de JSON (mapeo de la API a objetos Java).**

**Maven: Herramienta de gestión de dependencias y construcción del proyecto.**

# Cómo Ejecutar el Proyecto

**Requisitos Previos**

Asegúrate de tener instalado en tu sistema:

- Java Development Kit (JDK) 17 o superior.

- Apache Maven.

- PostgreSQL.

### Configuración

1. Clona el repositorio:
   
`git clone https://github.com/tu-usuario/nombre-del-repositorio.git
cd nombre-del-repositorio`


2. Configura la base de datos:

*Crea una base de datos en **PostgreSQL** llamada **literalura.***

***Importante:** Configura tus credenciales de base de datos como variables de entorno en tu sistema para evitar subirlas a GitHub.*

***Ejemplo en terminal (Linux/macOS):***

`export SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}`

`export SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}`

`export SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}`

3. Ejecuta la aplicación:

*Abre una terminal en la raíz del proyecto.*

*Ejecuta el siguiente comando para compilar y arrancar la aplicación:*

`mvn spring-boot:run`

# Autor: 

*Maria Soledad Acri* 

* GitHub: https://github.com/MariaAcri98
* Linkedin: https://www.linkedin.com/in/maria-acri-75a4a0343/
