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

        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", namingIp);
        props.put("org.omg.CORBA.ORBInitialPort", namingPort);

        ORB orb = ORB.init(new String[]{}, props);

        try {
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            UsuarioService usuarioService = UsuarioServiceHelper.narrow(ncRef.resolve_str("UsuarioService"));
            AsistenciaService asistenciaService = AsistenciaServiceHelper.narrow(ncRef.resolve_str("AsistenciaService"));

            String userId = "userId";
            //asignar por teclado
            System.out.println("Ingrese su ID de usuario:");
            userId = System.console().readLine();

            // Validar usuario y registrar asistencia
            System.out.println("Validando usuario...");
            if (usuarioService.validarUsuario(userId)) {
                asistenciaService.registrarAsistencia(userId);
                System.out.println(asistenciaService.generarReporte(userId));
            } else {
                System.out.println("Usuario no válido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
