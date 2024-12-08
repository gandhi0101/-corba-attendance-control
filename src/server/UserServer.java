package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

import java.util.Properties;

public class UserServer {
    public static void main(String[] args) {
        ORB orb = null;
        try {
            // Verifica si se pasa el argumento de conexión
            if (args.length < 2) {
                System.err.println("Uso: java server.UserServer <naming_ip:naming_port> <local_ip>");
                System.exit(1);
            }

            // Divide el primer argumento en IP y puerto del Naming Service
            String[] namingInfo = args[0].split(":");
            if (namingInfo.length != 2) {
                System.err.println("Formato incorrecto. Use <naming_ip:naming_port>.");
                System.exit(1);
            }
            String namingServiceIp = namingInfo[0];
            String namingServicePort = namingInfo[1];

            // Segundo argumento: IP local del servidor
            String localIp = args[1];

            // Configura las propiedades del ORB para conexiones remotas y la IP local
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", namingServiceIp);
            props.put("org.omg.CORBA.ORBInitialPort", namingServicePort);
            props.put("com.sun.CORBA.ORBServerHost", localIp); // IP local del servidor

            // Inicializa el ORB con las propiedades
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
