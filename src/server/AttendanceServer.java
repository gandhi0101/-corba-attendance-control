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

        String[] namingInfo = args[0].split(":");
        String namingIp = namingInfo[0];
        String namingPort = namingInfo[1];
        String localIp = args[1];

        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialHost", namingIp);
        props.put("org.omg.CORBA.ORBInitialPort", namingPort);
        props.put("com.sun.CORBA.ORBServerHost", localIp);

        ORB orb = ORB.init(new String[]{}, props);

        AttendanceServiceImpl servant = new AttendanceServiceImpl();
        ORBSetup.startORB(new String[]{}, servant, "AsistenciaService");

        System.out.println("AsistenciaService listo y esperando conexiones...");
        orb.run();
    }
}
