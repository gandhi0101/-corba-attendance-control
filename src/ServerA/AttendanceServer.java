package serverA;

import utils.ORBSetup;
import AsistenciaModule.*;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class AttendanceServer {
    public static void main(String[] args) {
        try {
            // Inicializa el ORB
            ORB orb = ORBSetup.initializeORB(args);

            // Obtén una referencia al POA y actívala
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Crea una instancia del servicio y registra su implementación
            AttendanceServiceImpl attendanceService = new AttendanceServiceImpl();
            attendanceService.setORB(orb);

            // Obtén la referencia al objeto CORBA
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(attendanceService);
            AsistenciaModule.AttendanceService href = AttendanceServiceHelper.narrow(ref);

            // Registra el servicio en el Naming Service
            org.omg.CORBA.Object namingContextObj = orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt namingContext = org.omg.CosNaming.NamingContextExtHelper.narrow(namingContextObj);

            org.omg.CosNaming.NameComponent[] name = namingContext.to_name("AttendanceService");
            namingContext.rebind(name, href);

            System.out.println("AttendanceService listo y registrado...");

            // Mantén el servidor corriendo
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
