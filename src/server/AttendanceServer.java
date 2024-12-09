package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

import java.util.Properties;

public class AttendanceServer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java server.AttendanceServer <naming_ip:naming_port> <local_ip>");
            System.exit(1);
        }

        // Desglosa los argumentos
        String[] namingInfo = args[0].split(":");
        String namingIp = namingInfo[0];
        String namingPort = namingInfo[1];
        String localIp = args[1];

        // Configura las propiedades del ORB
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", namingIp);
        props.put("org.omg.CORBA.ORBInitialPort", namingPort);
        props.put("com.sun.CORBA.ORBServerHost", localIp); // Configura la IP local

        System.out.println("Propiedades del ORB configuradas: " + props);

        try {
            // Inicializa el ORB
            ORB orb = ORB.init(new String[]{}, props);

            // Crea la implementación del servicio de asistencias
            AttendanceServiceImpl servant = new AttendanceServiceImpl();

            // Registra el servicio en el Naming Service con el nombre "AsistenciaService"
            ORBSetup.startORB(new String[]{}, servant, "AsistenciaService");

            System.out.println("AsistenciaService listo y esperando conexiones...");

            // Mantiene el servidor activo
            orb.run();
        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar el servidor de asistencias.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
