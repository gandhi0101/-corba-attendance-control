package server;

import utils.ORBSetup;

public class AttendanceServer {
    public static void main(String[] args) {
        try {
            // Crea la instancia del servicio de asistencias
            AttendanceServiceImpl servant = new AttendanceServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "AsistenciaService"
            ORBSetup.startORB(args, servant, "AsistenciaService");

            System.out.println("AsistenciaService estA listo y esperando conexiones...");

            // Mantiene el servidor activo para atender solicitudes
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
