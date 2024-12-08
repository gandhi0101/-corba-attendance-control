package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

public class AttendanceServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
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
