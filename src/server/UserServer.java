package server;

import utils.ORBSetup;
import server.UserServiceImpl;

public class UserServer {
    public static void main(String[] args) {
        try {
            // Crea la implementación del servicio
            UserServiceImpl servant = new UserServiceImpl();

            // Registra el servicio en el Naming Service
            ORBSetup.startORB(args, servant, "UsuarioService");

            System.out.println("UsuarioService está listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
