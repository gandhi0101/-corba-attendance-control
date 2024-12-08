package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

public class AttendanceServer {
    @SuppressWarnings("ImplicitArrayToString")
    public static void main(String[] args) {
        System.err.println(args);
        ORB orb = null;
        try {
            // Inicializa el ORB
            orb = ORB.init(args, null);
            

            // Crea la instancia del servicio de asistencias
            AttendanceServiceImpl servant = new AttendanceServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "AsistenciaService"
            ORBSetup.startORB(args, servant, "AsistenciaService");

            System.out.println("AsistenciaService está listo y esperando conexiones...");

            // Mantiene el servidor activo para atender solicitudes
            orb.run();

        } catch (org.omg.CORBA.COMM_FAILURE e) {
            System.err.println("Error de comunicación: No se pudo conectar al Naming Service.");
            e.printStackTrace();
            System.exit(1); // Código de salida 1 indica error crítico
        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar el servidor de asistencias.");
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (orb != null) {
                try {
                    orb.destroy(); // Limpia recursos si el ORB fue inicializado
                } catch (Exception e) {
                    System.err.println("Error al destruir el ORB: " + e.getMessage());
                }
            }
        }
    }
}
