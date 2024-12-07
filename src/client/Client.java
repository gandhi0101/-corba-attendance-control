package client;

import Asistencia.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resuelve el servicio de usuarios
            UsuarioService usuarioService = UsuarioServiceHelper.narrow(ncRef.resolve_str("UsuarioService"));

            // Resuelve el servicio de asistencias
            AsistenciaService asistenciaService = AsistenciaServiceHelper.narrow(ncRef.resolve_str("AsistenciaService"));

            // Prueba las operaciones
            String userId = "user1";
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
