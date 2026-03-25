# **NovaBank**        ·Tu banco, el banco de todos·

---
## Descripción
Para el desarrollo del banco NovaBank perteneciente a la actividad de la empresa NttData, en el ámbito de formación se ha requerido la creación de una aplicación en Java, en la que se han hecho uso de los principios generales que toda aplicación debe tener:

- **Encapsulamiento**: Cada clase debe controlar su propio estado y exponer solo lo necesario.
- **Separación de responsabilidades**: Cada clase debe de tener una única responsabilidad.
- **No duplicar lógica** en distintas clases.
- **Cohesión** entre métodos de distintas clases.
- **Acoplamiento bajo**: Evitar que las clases dependan fuertemente de otras.
- **Uso de interfaces**: Evitando que en futuros proyectos, al realizar modificaciones se tenga que cambiar la lógica existente.
- **Validación y manejo de errores**: Toda operación, por simple que sea, estará validada con pruebas unitarias.
<details>
  <summary>Resumen de la arquitectura</summary>

| Capa         | Clases                                                 | Responsabilidad                                                                  |
|--------------|--------------------------------------------------------|----------------------------------------------------------------------------------|
| model        | `Cliente`, `Cuenta`, `Movimiento`, `TipoMovimiento`    | Datos y operaciones internas (solo `Cuenta` mueve dinero y registra movimientos) |
| repository   | `ClienteRepository`, `CuentaRepository`                | Almacenamiento en memoria (usando `Map`) y búsquedas rápidas                     |
| service      | `ClienteService`, `CuentaService`                      | Lógica de negocio: crea clientes, crea cuentas, orquesta operaciones financieras |
| menus / main | `Main`, `MenuCliente`, `MenuCuentas`, `MenuOperaciones` | Interfaz de consola, interacción con el usuario                                  |

</details>

---

## Funcionalidad de cada clase

---
<details>
    <summary>Packete Model</summary>
   El paquete model  tiene como finalidad encapsular las entidades principales del dominio y las operaciones asociadas a las mismas.

Dentro de este paquete se definen las reglas que gobiernan el comportamiento de las cuentas bancarias, así como las operaciones fundamentales que pueden realizarse sobre ellas:

- Ingreso de fondos.
- Retirada de fondos.
- Transferencia entre cuentas.

Estas operaciones no solo modifican el estado interno de las entidades, sino que también garantizan la coherencia del sistema aplicando las validaciones necesarias y registrando los movimientos correspondientes.
<div style="margin-left:20px;">
<details>
    <summary>Clase Cuenta</summary>

Contiene las siguientes variables:
- numeroCuenta.
- titular 
- saldo 
- fechaCreacion 
- Lista de Movimientos = movimientos
 
Contiene las siguientes funciones:
- ingresar()
- retirar()
- transferirA()

Contiene los getters:
- getNumeroCuenta()
- getTitular()
- getSaldo()
- getFechaCreacion()
- getMovimientos()

En esta clase y después de cada método, dependiendo del método que realicemos, ya sea **ingresar, retirar o transferir**, llamamos a la clase movimientos y realizamos el registro del movimiento.

</details>


<details>
    <summary> Clase Cliente</summary>
Esta clase nos permite registrar a los clientes, para ello hemos necesitado guardar las siguientes variables:

- ID del cliente.
- Nombre
- Apellidos
- Email
- Teléfono

De estas variables hemos tenido en cuenta que el DNI contenga al menos "@" y "."
El ID del cliente es autogenerado automáticamente.
La clase cliente también cuenta con los gettes y Setters de algunos de los atributos anteriormente nombrados.


</details>


<details><summary>Clase Movimiento</summary>

En la clase movimiento hemos definido 3 variables, teniendo en cuenta que hay otra clase Enum que tiene que inicializarse antes.
Las variables que hemos creado para esta clase son:

- El tipo de movimiento: tipo (pertenenciente a TipoMovimiento)
- importe
- fecha (con LocalDateTime).

El constructor cuenta con las variables tipo e importe. Aunque dentro usamos también el LocalDateTime.now().

Hemos creado los getters de las variables TipoMovimiento, Importe y getFecha().


</div>
</details>
</details>









<details><summary>Packete Repository</summary>

Dentro de este packete vamos a encontrar dos clases:

 - ClienteRepository
 - CuentaRepository

Estas clases han sido usadas como almacenamiento, es el lugar dónde se ha implementado el uso de los Map, con la finalidad de poder realizar búsquedas rápidas.

<div style="margin-left:20px;">
<details><summary>Clase Cliente Repository</summary>

En esta clase hemos creado las siguientes variables, todas ellas haciendo uso de Map, new HashMap, la finalidad de usar esta función es poder acceder a los datos de manera más rápida, creando "etiquetas" para el conjunto de datos dado.

- clientesPorDni, se guarda el dni junto con el cliente completo.
- clientePoremail, se guarda el dni junto con el cliente.
- clientesPorTelefono, se guarda el teléfono junto con el cliente al que le pertenece.
- clientesPorID, se guarda el id del cliente junto al cliente.

Todos estos HashMap nos permiten llevar a cabo las siguientes funciones definidas en la clase:

- buscarPorDni(dni): simplemente clientesPorDni(dni) y tendríamos la variable correspondiente.
- buscarPorEmail(email)
- buscarPorTelefono(telefono)
- buscarPorID(id)
- buscarTodos(), para devolver una lista con todos los clientes que tenemos.

</details>

<details><summary>Clase CuentaRepository</summary>
Esta clase actúa igual que la clase ClienteRepository aunque es algo más sencilla.
Únicamente tenemos un HasMap de la variable cuentas, dónde vamos a ir añadiendo aquellas cuentas que se van creando.
Contamos con las siguientes funciones:

- public void guardar(Cuenta cuenta), añadimos al HashMap la cuenta recién creada.
- buscarPorNumeroCuenta(String numeroCuenta)

A la hora de crear el Listar Cuentas, he creado un ArrayList para devolver los valores del HashMap cuentas.
También tenemos un buscarPorClienteID(id), en el que vamos recorriendo cada valor de cuentas, cuentas.values() y lo comparamos las variables del ID del titular con el id, mediante el uso de un equals.

</details>

</div>
</details>

<details><summary>Packete Service</summary>
En este packete hacemos la lógica del negocio, creamos clientes, creamos cuentas o bien llamamos a las funciones para poder realizar las operaciones financieras que ya se encontraban definidas.
En este packete vamos a encontrar las siguientes clases:

- ClienteService
- CuentaService

<div style="margin-left:20px;">
<details><summary>Clase ClienteService</summary>
En esta clase se elaboran tanto las validaciones de los requisitos como la lógica de la creación del cliente.
Como variables hemos traído el paquete de Cliente repository, con el objetivo de poder acceder a todas las funciones que hemos creado anteriormente y usarlas para validar que las operaciones financieras se estén realizando de manera correcta, sin tener saldos en negativo por ejemplo.
Encontramos la función Cliente crearCliente() y las condiciones que se verifican son:

- if(buscarPorDni no es null), es porque ya existe alguien con ese dni, no puede ser cliente nuevo y por tanto lanzamos excepción.
- if(buscarPorEmail no es null), ya existe un cliente con ese email.
- if(buscarPortelefono no es null), teléfono ya registrado.
- if(emails no contiene "@" o "."), el email está mal diseñado y tiene que seguir una estructura ejemplo@dominio.com

Si de estas condiciones, ninguna ha hecho saltar el error entonces se crea el nuevo cliente.
Además, en esta clase definimos otras funciones para llevarlas a cabo desde Cliente service:

- encontrarPorDni(dni){ return repository.buscarPorDni(dni)
- encontrarPorId(long id){
- return repository.buscarPorId(id)
- listarClientes() {return repository.buscarTodos();


</details>

<details><summary>Clase CuentaService</summary>
Para el desarrollo de esta clase hemos necesitado, 

- CuentaRepository, dónde quedaban definidas las cuentas que han sido creadas, y eran guardadas en el hashMap()
- ClienteService, dónde ya se reciben los clientes que han sido creados y validados, con sus respectivos atributos.

Las funciones que se han realizado en la clase **CuentaService** son:

- crearCuenta(pidiendo el idCliente), para ello se verifica que el cliente no sea null.
- buscarPorNumeroCuenta(numeroCuenta), verificamos que exista la cuenta, y como es un HashMap, le devolvemos el valor.
- listarCuentasPorClientes(clienteID), buscamos al cliente por su ID y devolvemos la cuentas que tenga.
- Operaciones
  - ingresar()
  - retirar()
  - transferirA()


</details>
<details><summary>Clase Consulta</summary>
Esta clase tiene como objetivo poder consultar la información que se queda registrada en clase Cuentas para poder acceder más tarde desde el menú.

Necesitamos la variable que proviene desde **CuentaService** para poder acceder a la información que tenemos de cada cuenta, las funciones que nos permite esta clase realizar son las siguientes:

- consultarSaldo(numeroCuenta).
- obtenerHistorial(numeroCuenta), se ven todos los movimientos de esa cuenta registrados en una lista.
- obtenerMovimientosPorRango(numeroCuenta, fecha_inicio, fecha_fin), como teníamos guardada la variable de fecha, podemos ver las actividad de una cuenta entre dos rangos de valores que en este caso es fecha.




</details>
</div>
</details>

---

Los menús interactivos de cada sección fueron creados de manera independiente, quedando creados los siguientes:

- MenuCliente
- MenuConsultas
- MenuCuentas
- MenuOperaciones

Y desde el **Main** llamamos al menú que le corresponda aparecer por pantalla.

---

# Diseño del Modelo de Datos.

Aunque no hemos tenido que utilizar una base de datos, hemos diseñado el modelo desde el principio, hemos realizado el estudio de las distintas entidades y visto como actúan entre sí, al igual que el tipo de relación que tienen.

Relaciones principales:

- Cliente → Cuenta: un cliente puede tener muchas cuentas (1:N)
- Cuenta → Movimiento: una cuenta puede tener muchos movimientos (1:N)
- Movimiento → Cuenta: un movimiento pertenece a una única cuenta

Un ejemplo visual de como se vería nuestro eschema.sql de manera esquemática:
<details>
<summary>Desplegar Esquema: </summary>

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


### Uso de Inteligencia Artificial como herramienta

Durante el desarrollo de esta actividad se ha empleado Inteligencia Artificial como herramienta de apoyo técnico y consultivo. Su utilización ha estado orientada principalmente a contrastar decisiones de diseño, validar enfoques arquitectónicos y asegurar que las soluciones adoptadas estuvieran alineadas con buenas prácticas de desarrollo.

Asimismo, se han realizado consultas puntuales para la revisión de código previamente implementado, con el objetivo de detectar posibles errores de implementación, inconsistencias lógicas o mejoras estructurales. Estas revisiones han tenido un carácter complementario y no sustitutivo del análisis propio.

Todas las decisiones técnicas, modificaciones y validaciones finales del código han sido realizadas y supervisadas por el autor. En consecuencia, asumo la responsabilidad íntegra sobre el diseño, la implementación y cualquier posible error que pudiera existir en el presente proyecto.
