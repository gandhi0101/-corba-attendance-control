package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

import java.util.Properties;

public class AttendanceServer {
    public static void main(String[] args) {
        ORB orb = null;

        try {
            // Verifica si se pasan los argumentos necesarios
            if (args.length < 2) {
                System.err.println("Uso: java server.AttendanceServer <naming_ip:naming_port> <local_ip>");
                System.exit(1);
            }

            // Procesa el primer argumento (IP y puerto del Naming Service)
            String[] namingInfo = args[0].split(":");
            if (namingInfo.length != 2) {
                System.err.println("Formato incorrecto. Use <naming_ip:naming_port>.");
                System.exit(1);
            }
            String namingServiceIp = namingInfo[0];
            String namingServicePort = namingInfo[1];

            // Segundo argumento: IP local del servidor
            String localIp = args[1];

            System.out.println("Conectando al Naming Service en " + namingServiceIp + ":" + namingServicePort);
            System.out.println("IP local configurada como " + localIp);

            // Configura las propiedades del ORB
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", namingServiceIp);
            props.put("org.omg.CORBA.ORBInitialPort", namingServicePort);
            props.put("com.sun.CORBA.ORBServerHost", localIp); // Configura la IP local del servidor

            // Inicializa el ORB con las propiedades configuradas
            orb = ORB.init(new String[]{}, props);

            // Crea la implementación del servicio de asistencias
            AttendanceServiceImpl servant = new AttendanceServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "AsistenciaService"
            ORBSetup.startORB(new String[]{}, servant, "AsistenciaService");

            System.out.println("AsistenciaService está listo y esperando conexiones...");

            // Mantiene el servidor activo para atender solicitudes
            orb.run();

        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar el servidor de asistencias.");
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
