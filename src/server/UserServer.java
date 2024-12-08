package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

public class UserServer {
    public static void main(String[] args) {
        ORB orb = null;
        try {
            // Inicializa el ORB
            orb = ORB.init(args, null);

            // Crea la implementación del servicio de usuarios
            UserServiceImpl servant = new UserServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "UsuarioService"
            ORBSetup.startORB(args, servant, "UsuarioService");

            System.out.println("UsuarioService está listo y esperando conexiones...");

            // Mantiene el servidor activo para atender solicitudes
            orb.run();

        } catch (org.omg.CORBA.COMM_FAILURE e) {
            System.err.println("Error de comunicación: No se pudo conectar al Naming Service.");
            e.printStackTrace();
            System.exit(1); // Código de salida 1 indica error crítico
        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar el servidor de usuarios.");
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
