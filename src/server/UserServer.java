package server;

import utils.ORBSetup;
import org.omg.CORBA.ORB;

import java.util.Properties;

public class UserServer {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java server.UserServer <naming_ip:naming_port> <local_ip>");
            System.exit(1);
        }

        String[] namingInfo = args[0].split(":");
        String namingIp = namingInfo[0];
        String namingPort = namingInfo[1];
        String localIp = args[1];

        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", namingIp);
        props.put("org.omg.CORBA.ORBInitialPort", namingPort);
        props.put("com.sun.CORBA.ORBServerHost", localIp);

        System.out.println("Propiedades configuradas para el ORB: " + props);

        ORB orb = ORB.init(new String[]{}, props);

        UserServiceImpl servant = new UserServiceImpl();
        ORBSetup.startORB(new String[]{}, props, servant, "UsuarioService");


        System.out.println("UsuarioService listo y esperando conexiones...");
        orb.run();
    }
}
