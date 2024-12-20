
# CORBA Attendance Control

## Descripción
Aplicación distribuida que utiliza CORBA para gestionar usuarios y control de asistencias.

## Requisitos
- JDK 8
- Naming Service (incluido en CORBA).

## Cómo ejecutar
1. **Compilar el IDL**:
   ```bash
   idlj -fall idl/Asistencia.idl
   ```
2. **Compilar los servicios y cliente**:
   ```bash
   javac -encoding UTF-8 -d build/ src/**/*.java
   ```
3. **Ejecutar el Naming Service**:
   ```bash
   tnameserv -ORBInitialPort 900
   ```
4. **Ejecutar los servicios**:
   ```bash
   
   java -cp build/ server.UserServer naming_ip:port ip_desttino
   java -cp build/ server.AttendanceServer naming_ip:port ip_desttino

   ```
5. **Ejecutar el cliente**:
   ```bash
   java -cp build/ client.Client naming_ip:port ip_desttino
   ```



## estructura del proyecto

```
CORBA-ATTENDANCE-CONTROL/
│
├── build/                   # Archivos compilados y generados
├── docs/                    # Documentación del proyecto
│   └── README.md
├── idl/                     # Definición de interfaces en CORBA
│   └── Asistencia.idl
├── resources/               # Archivos de configuración
├── src/                     # Código fuente
│   ├── client/              # Lógica del cliente
│   │   └── Client.java
│   ├── server/              # Implementación de los servicios
│   │   ├── AttendanceServiceImpl.java
│   │   └── UserServiceImpl.java
│   └── utils/               # Utilidades comunes
│       └── ORBSetup.java
└── tests/                   # Pruebas unitarias
    └── TestCases.java       # Clase base para pruebas
```

