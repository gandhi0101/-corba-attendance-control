package client;

import Asistencia.UsuarioService;
import Asistencia.UsuarioServiceHelper;
import Asistencia.AsistenciaService;
import Asistencia.AsistenciaServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Properties;

public class Client {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java client.Client <naming_ip:naming_port>");
            System.exit(1);
        }

        String[] namingInfo = args[0].split(":");
        String namingIp = namingInfo[0];
        String namingPort = namingInfo[1];

        // Configura las propiedades del ORB
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", namingIp); // Dirección del NameService
        props.put("org.omg.CORBA.ORBInitialPort", namingPort); // Puerto del NameService

        System.out.println("Propiedades configuradas para el ORB: " + props);

        try {
            // Inicializa el ORB
            ORB orb = ORB.init(new String[]{}, props);

            // Obtiene el Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resuelve los servicios remotos
            UsuarioService usuarioService = UsuarioServiceHelper.narrow(ncRef.resolve_str("UsuarioService"));
            AsistenciaService asistenciaService = AsistenciaServiceHelper.narrow(ncRef.resolve_str("AsistenciaService"));

            // Solicita al usuario su ID
            while (true) {
                System.out.println("Ingrese su ID de usuario:");
                String userId = System.console().readLine();

                // Validar usuario y registrar asistencia
                System.out.println("Validando usuario...");
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
            System.err.println("Error al conectar con el servicio.");
        }
    }
}
