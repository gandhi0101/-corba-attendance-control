package client;

import utils.ORBSetup;
import AsistenciaModule.*;

public class Client {

    public static void main(String[] args) {
        try {
            org.omg.CORBA.ORB orb = ORBSetup.initializeORB(args);

            // Obtén referencia al objeto remoto desde el Naming Service
            org.omg.CORBA.Object objRef = orb.string_to_object("corbaloc:iiop:localhost:1050/AttendanceService");
            AttendanceService attendanceService = AttendanceServiceHelper.narrow(objRef);

            // Solicita al usuario su ID
            while (true) {
                System.out.println("Ingrese su ID de usuario:");
                String userId = System.console().readLine();

                // ingrese su password
                System.out.println("Ingrese su password:");
                String password = System.console().readLine();

                boolean success = attendanceService.markAttendance(userId, password);
                System.out.println("Attendance marked: " + success);

                if (usuarioService.validarUsuario(userId)) {
                    System.out.println("Usuario válido. Registrando asistencia...");
                    asistenciaService.registrarAsistencia(userId);
                    System.out.println(asistenciaService.generarReporte(userId));
                } else {
                    System.out.println("Usuario no válido.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
