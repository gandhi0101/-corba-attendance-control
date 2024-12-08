package server;

import utils.ORBSetup;
import server.UserServiceImpl;

public class UserServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            // Crea la implementación del servicio
            UserServiceImpl servant = new UserServiceImpl();

            // Registra el servicio en el Naming Service
            ORBSetup.startORB(args, servant, "UsuarioService");

            System.out.println("UsuarioService estA listo y esperando conexiones...");
            // Mantiene el servidor activo para atender solicitudes
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
