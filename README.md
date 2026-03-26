# **NovaBank**        ·Tu banco, el banco de todos·

---

![bankpicture.jpg](pictures/bankpicture.jpg)

---
# Índice

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Arquitectura del Sistema](#arquitectura-del-sistema)
3. [Modelo de Datos](#modelo-de-datos)
4. [Diagrama Entidad-Relación](#diagrama-entidad-relación)
5. [Estructura del Proyecto](#estructura-del-proyecto)
6. [Diseño de Servicios y Responsabilidades](#diseño-de-servicios-y-responsabilidades)
7. [Gestión de Excepciones](#gestión-de-excepciones)
8. [Tests y Estrategia de Validación](#tests-y-estrategia-de-validación)
9. [Tecnologías Utilizadas](#tecnologías-utilizadas)
10. [Requisitos del Sistema](#requisitos-del-sistema)
11. [Instalación y Compilación](#instalación-y-compilación)
12. [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
13. [Ejecución de Tests](#ejecución-de-tests)
14. [Uso de Inteligencia Artificial](#uso-de-inteligencia-artificial)
15. [Flujo de Trabajo con Git](#flujo-de-trabajo-con-git)
16. [Repositorio en GitHub](#repositorio-en-github)

--- 

## Descripción del Proyecto

**NovaBank** es una aplicación desarrollada en Java como parte de una actividad formativa en el entorno de NTT Data. El objetivo del proyecto es implementar un sistema bancario simplificado que permita gestionar clientes, cuentas y operaciones financieras, aplicando principios sólidos de diseño y buenas prácticas de desarrollo.

En este módulo se ha construido una aplicación de consola estructurada por capas, en la que se implementan las siguientes funcionalidades principales:

- Registro y gestión de clientes.
- Creación y administración de cuentas bancarias.
- Operaciones financieras: ingreso, retirada y transferencia.
- Consulta de saldo e historial de movimientos.
- Validación de datos y control de errores mediante excepciones personalizadas.
- Verificación del comportamiento mediante pruebas unitarias con JUnit 5.

El diseño del sistema se ha basado en los siguientes principios fundamentales:

- **Encapsulamiento**: Cada clase gestiona su propio estado y expone únicamente lo necesario.
- **Separación de responsabilidades**: Cada componente tiene una única responsabilidad claramente definida.
- **Alta cohesión y bajo acoplamiento**: Se minimizan dependencias innecesarias entre clases.
- **No duplicación de lógica**: Las reglas de negocio se centralizan en la capa de servicios.
- **Uso de interfaces y abstracción lógica**: Facilitando la mantenibilidad y futura evolución del sistema.
- **Validación y manejo de errores**: Todas las operaciones están protegidas mediante validaciones y pruebas unitarias.

El almacenamiento de datos se realiza en memoria mediante estructuras `Map`, simulando el comportamiento de persistencia y permitiendo búsquedas eficientes.


## Arquitectura del Sistema

La aplicación sigue una arquitectura por capas, separando claramente las responsabilidades del dominio, la lógica de negocio, el acceso a datos y la interacción con el usuario.

El objetivo de esta estructura es garantizar:

- Mantenibilidad del sistema.
- Bajo acoplamiento entre componentes.
- Alta cohesión dentro de cada módulo.
- Facilidad de extensión futura.


<details>
  <summary>Resumen de la arquitectura</summary>

| Capa         | Clases                                                 | Responsabilidad                                                                  |
|--------------|--------------------------------------------------------|----------------------------------------------------------------------------------|
| model        | `Cliente`, `Cuenta`, `Movimiento`, `TipoMovimiento`    | Datos y operaciones internas (solo `Cuenta` mueve dinero y registra movimientos) |
| repository   | `ClienteRepository`, `CuentaRepository`                | Almacenamiento en memoria (usando `Map`) y búsquedas rápidas                     |
| service      | `ClienteService`, `CuentaService`                      | Lógica de negocio: crea clientes, crea cuentas, orquesta operaciones financieras |
| menus / main | `Main`, `MenuCliente`, `MenuCuentas`, `MenuOperaciones` | Interfaz de consola, interacción con el usuario                                  |

</details>

### Capa `model`

Contiene las entidades del dominio y las reglas internas asociadas a su comportamiento.  
La clase `Cuenta` es la única responsable de modificar el saldo y registrar movimientos, garantizando la coherencia del estado financiero.

### Capa `repository`

Implementa almacenamiento en memoria mediante estructuras `HashMap`, permitiendo búsquedas rápidas por claves como DNI, ID o número de cuenta.

Esta capa abstrae el acceso a datos, facilitando una futura sustitución por persistencia real (por ejemplo, base de datos relacional).

### Capa `service`

Centraliza la lógica de negocio del sistema:

- Validaciones.
- Control de duplicidades.
- Gestión de excepciones.
- Coordinación entre repositorios.
- Orquestación de operaciones financieras.

Evita que la lógica de negocio se disperse en otras capas.

### Capa de presentación (menús)

Gestiona la interacción con el usuario mediante consola.  
Se limita a recoger datos, mostrar información y delegar las operaciones en la capa de servicios.

No contiene lógica de negocio.

---

Esta arquitectura permite que cada capa evolucione de forma independiente, manteniendo el sistema estructurado y alineado con principios de diseño orientado a objetos.

---

## Modelo de Datos

Aunque la aplicación no utiliza una base de datos real, el sistema ha sido diseñado partiendo de un modelo relacional conceptual. Este diseño previo permite estructurar correctamente las entidades, sus atributos y sus relaciones, asegurando coherencia y escalabilidad futura.

El almacenamiento actual se realiza en memoria mediante estructuras `Map`, pero el modelo está preparado para una posible migración a una base de datos relacional sin necesidad de rediseñar el dominio.

### Entidades principales

El sistema se compone de tres entidades fundamentales:

- **Cliente**
- **Cuenta**
- **Movimiento**

### Relaciones entre entidades

- **Cliente → Cuenta**:  
  Un cliente puede tener múltiples cuentas (relación 1:N).

- **Cuenta → Movimiento**:  
  Una cuenta puede registrar múltiples movimientos (relación 1:N).

- **Movimiento → Cuenta**:  
  Cada movimiento pertenece exclusivamente a una cuenta (relación N:1).

Estas relaciones garantizan trazabilidad completa de las operaciones financieras y coherencia en la gestión del saldo.

---

<details>
<summary>Esquema relacional conceptual</summary>

```text
CLIENTES
+----------------------+
| id (PK)              |
| nombre               |
| apellidos            |
| dni (UNIQUE)         |
| email (UNIQUE)       |
| telefono (UNIQUE)    |
+----------------------+
            1
            |
            N
CUENTAS
+----------------------+
| id (PK)              |
| numero_cuenta        |
| cliente_id (FK)      |
| saldo                |
+----------------------+
            1
            |
            N
MOVIMIENTOS
+----------------------+
| id (PK)              |
| cuenta_id (FK)       |
| tipo                 |
| cantidad             |
| fecha                |
+----------------------+
```

</details>

---

### Consideraciones de diseño

- Las claves primarias (`PK`) identifican de forma única cada entidad.
- Las claves foráneas (`FK`) modelan las relaciones entre entidades.
- Las restricciones `UNIQUE` evitan duplicidad en atributos críticos como DNI, email y teléfono.
- El diseño permite garantizar integridad referencial incluso en un entorno en memoria.

Este enfoque orientado a modelo facilita la mantenibilidad del sistema y asegura una base sólida para su evolución hacia una arquitectura con persistencia real.

## Estructura del Proyecto

El proyecto está organizado siguiendo una estructura modular por paquetes, alineada con la separación de responsabilidades definida en la arquitectura del sistema.

La disposición de carpetas sigue el estándar de proyectos Maven:

<details><summary>Desplegar árbol con las carpetas:</summary>

```text
El número de serie del volumen es EA34-D89B
C:.
│   .gitignore
│   pom.xml
│   README.md
│
├───.idea
│       .gitignore
│       casopractico.iml
│       compiler.xml
│       encodings.xml
│       jarRepositories.xml
│       misc.xml
│       vcs.xml
│       workspace.xml
│
├───pictures
│       bankpicture.jpg
│       img.png
│
├───src
│   ├───main
│   │   └───java
│   │       ├───com
│   │       │   └───novabank
│   │       │       │   Main.java
│   │       │       │
│   │       │       ├───exception
│   │       │       │       ClienteDuplicadoException.java
│   │       │       │       ClienteNoEncontradoException.java
│   │       │       │       ClienteNoPuedeDepositar.java
│   │       │       │       ClienteNoPuedeRetirar.java
│   │       │       │       ClienteNoTransfiere.java
│   │       │       │       CuentaNoEncontrada.java
│   │       │       │
│   │       │       ├───menus
│   │       │       │       MenuCliente.java
│   │       │       │       MenuConsultas.java
│   │       │       │       MenuCuentas.java
│   │       │       │       MenuOperaciones.java
│   │       │       │
│   │       │       ├───model
│   │       │       │       Cliente.java
│   │       │       │       Cuenta.java
│   │       │       │       Movimiento.java
│   │       │       │       TipoMovimiento.java
│   │       │       │
│   │       │       ├───repository
│   │       │       │       ClienteRepository.java
│   │       │       │       CuentaRepository.java
│   │       │       │
│   │       │       └───service
│   │       │               ClienteService.java
│   │       │               ConsultaService.java
│   │       │               CuentaService.java
│   │       │
│   │       └───main
│   │           └───java
│   │               └───com
│   │                   └───novabank
│   └───test
│       └───java
│               ClienteServiceTest.java
│               ConsultaServiceTest.java
│               CuentaServiceTest.java
│
└───target
    │   caso-practico-novabank-1.0-SNAPSHOT.jar
    │
    ├───classes
    │   └───com
    │       └───novabank
    │           │   Main.class
    │           │
    │           ├───exception
    │           │       ClienteDuplicadoException.class
    │           │       ClienteNoEncontradoException.class
    │           │       ClienteNoPuedeDepositar.class
    │           │       ClienteNoPuedeRetirar.class
    │           │       ClienteNoTransfiere.class
    │           │       CuentaNoEncontrada.class
    │           │
    │           ├───menus
    │           │       MenuCliente.class
    │           │       MenuConsultas.class
    │           │       MenuCuentas.class
    │           │       MenuOperaciones.class
    │           │
    │           ├───model
    │           │       Cliente.class
    │           │       Cuenta.class
    │           │       Movimiento.class
    │           │       TipoMovimiento.class
    │           │
    │           ├───repository
    │           │       ClienteRepository.class
    │           │       CuentaRepository.class
    │           │
    │           └───service
    │                   ClienteService.class
    │                   ConsultaService.class
    │                   CuentaService.class
    │
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    ├───maven-archiver
    │       pom.properties
    │
    ├───maven-status
    │   └───maven-compiler-plugin
    │       ├───compile
    │       │   └───default-compile
    │       │           createdFiles.lst
    │       │           inputFiles.lst
    │       │
    │       └───testCompile
    │           └───default-testCompile
    │                   createdFiles.lst
    │                   inputFiles.lst
    │
    ├───surefire-reports
    │       ClienteServiceTest.txt
    │       ConsultaServiceTest.txt
    │       CuentaServiceTest.txt
    │       TEST-ClienteServiceTest.xml
    │       TEST-ConsultaServiceTest.xml
    │       TEST-CuentaServiceTest.xml
    │
    └───test-classes
            ClienteServiceTest.class
            ConsultaServiceTest.class
            CuentaServiceTest.class

PS C:\Users\rrodrcha\IdeaProjects\casopractico>

```
</details>
