# Changelog

Todos los cambios relevantes de este repositorio se documentan en este archivo.

## 2026-10-05

### Added
- Estructura inicial del proyecto Maven en NetBeans.
- Archivo '.gitignore' para omitir archivos de compilación de NetBeans y Maven.
- Implementación de las 10 clases del modelo de dominio: 'CategoriaVehiculo', 'Cliente', 'ContratoAlquiler', 'DetalleContrato', 'DetalleFactura', 'Empleado', 'Factura', 'Mantenimiento', 'Sucursal' y 'Vehiculo'.
- Clase ejecutable `Main.java`.
- Documentación inicial del proyecto ('README.md' y 'CHANGELOG.md').

## 2026-10-05

### Added
- Implementación de atributos y métodos en las clases:
  - 'CategoriaVehiculo.java' (actualización de tarifas y consulta)
  - 'Cliente.java' (validación de licencia y contacto)
  - 'ContratoAlquiler.java' (asociaciones con Cliente/Vehículo y cálculo de montos)
  - 'DetalleContrato.java' (gestión de cargos adicionales)