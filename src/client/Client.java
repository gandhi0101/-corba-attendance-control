package client;

import Asistencia.UsuarioService;
import Asistencia.UsuarioServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Properties;

public class Client {
    public static void main(String[] args) {
        try {
            // Verifica si se pasa el argumento de conexión
            if (args.length < 1) {
                System.err.println("Uso: java client.Client <naming_ip:naming_port>");
                System.exit(1);
            }

            // Divide el argumento en IP y puerto del Naming Service
            String[] namingInfo = args[0].split(":");
            if (namingInfo.length != 2) {
                System.err.println("Formato incorrecto. Use <naming_ip:naming_port>.");
                System.exit(1);
            }
            String namingServiceIp = namingInfo[0];
            String namingServicePort = namingInfo[1];

            // Configura las propiedades del ORB
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", namingServiceIp);
            props.put("org.omg.CORBA.ORBInitialPort", namingServicePort);

            // Inicializa el ORB
            ORB orb = ORB.init(new String[]{}, props);

            // Obtiene una referencia al Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Busca el servicio de usuarios
            UsuarioService usuarioService = UsuarioServiceHelper.narrow(ncRef.resolve_str("UsuarioService"));

            // Ejemplo: Llama al método del servicio
            String userId = "user1";
            boolean isValid = usuarioService.validarUsuario(userId);
            System.out.println("Usuario válido: " + isValid);

        } catch (Exception e) {
            System.err.println("Error inesperado al conectar al servicio.");
            e.printStackTrace();
        }
    }
}
