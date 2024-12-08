package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

import java.util.Properties;

public class UserServer {
    public static void main(String[] args) {
        ORB orb = null;
        try {
            // Verifica si se pasa el argumento de conexión
            if (args.length < 1) {
                System.err.println("Uso: java server.UserServer <ip:puerto>");
                System.exit(1);
            }

            // Divide el argumento en IP y puerto
            String[] connectionInfo = args[0].split(":");
            if (connectionInfo.length != 2) {
                System.err.println("Formato incorrecto. Use <ip:puerto>.");
                System.exit(1);
            }
            String ip = connectionInfo[0];
            String port = connectionInfo[1];

            // Configura las propiedades del ORB
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", ip);
            props.put("org.omg.CORBA.ORBInitialPort", port);

            // Inicializa el ORB
            orb = ORB.init(new String[]{}, props);

            // Crea la implementación del servicio de usuarios
            UserServiceImpl servant = new UserServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "UsuarioService"
            ORBSetup.startORB(new String[]{}, servant, "UsuarioService");

            System.out.println("UsuarioService está listo y esperando conexiones...");

            // Mantiene el servidor activo para atender solicitudes
            orb.run();

        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar el servidor de usuarios.");
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (orb != null) {
                try {
                    orb.destroy();
                } catch (Exception e) {
                    System.err.println("Error al destruir el ORB: " + e.getMessage());
                }
            }
        }
    }
}
