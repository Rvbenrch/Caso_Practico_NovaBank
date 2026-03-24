# NovaBank
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