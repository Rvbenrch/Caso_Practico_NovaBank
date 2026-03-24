# NovaBank
## Descripción
<details>
  <summary>Resumen de la arquitectura</summary>

| Capa         | Clases                                                 | Responsabilidad                                                                  |
|--------------|--------------------------------------------------------|----------------------------------------------------------------------------------|
| model        | `Cliente`, `Cuenta`, `Movimiento`, `TipoMovimiento`    | Datos y operaciones internas (solo `Cuenta` mueve dinero y registra movimientos) |
| repository   | `ClienteRepository`, `CuentaRepository`                | Almacenamiento en memoria (usando `Map`) y búsquedas rápidas                     |
| service      | `ClienteService`, `CuentaService`                      | Lógica de negocio: crea clientes, crea cuentas, orquesta operaciones financieras |
| menus / main | `Main`, `MenuCliente`, `MenuCuentas`, `MenuOperaciones` | Interfaz de consola, interacción con el usuario                                  |

</details>