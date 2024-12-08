package utils;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class ORBSetup {
    public static void startORB(String[] args, Servant servant, String name) {
        try {
            // Inicializa el RootPOA y activa el POAManager
            ORB orb = ORB.init(args, null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Convierte el Servant en un objeto CORBA
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servant);

            // Obtiene el Naming Service y registra el objeto
            NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, ref);

            System.out.println(name + " registrado en el Naming Service.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fallo al configurar el ORB");
        }
    }
}
