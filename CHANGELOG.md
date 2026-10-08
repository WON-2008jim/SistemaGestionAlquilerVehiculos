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

## 2026-10-07
### Added
- Implementación de atributos y métodos en las clases:
  - 'Sucursal.java' (registro de sucursales, actualización de contacto y consulta de información)
  - 'Vehiculo.java' (cambio de estado, registro de kilometraje y consulta de disponibilidad)
  - 'Mantenimiento.java' (asociación con Vehiculo, registro y finalización de mantenimientos)

## 2026-10-07
### Added
- Implementación de atributos y métodos en las clases:
  - 'Factura.java' (generación de factura a partir de un contrato de alquiler, cálculo del total tributario e impresión del comprobante contable)
  - 'DetalleFactura.java' (desglose de conceptos individuales y cálculo del subtotal por renglón)
  - 'Empleado.java' (autenticación de credenciales de usuario e ingreso al sistema, y consulta de perfil laboral)