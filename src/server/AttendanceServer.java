package server;

import utils.ORBSetup;

public class AttendanceServer {
    public static void main(String[] args) {
        try {
            // Crea la instancia del servicio de asistencias
            AttendanceServiceImpl servant = new AttendanceServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "AsistenciaService"
            ORBSetup.startORB(args, servant, "AsistenciaService");

            System.out.println("AsistenciaService está listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
