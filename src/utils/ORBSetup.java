package utils;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

import java.util.Properties;

public class ORBSetup {
    public static void startORB(String[] args, Properties orbProps, Servant servant, String name) {
        try {
            // Inicializa el ORB con las propiedades proporcionadas
            ORB orb = ORB.init(args, orbProps);

            // Inicializa el RootPOA y activa el POAManager
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Convierte el Servant en un objeto CORBA
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servant);

            // Obtiene el Naming Service y registra el objeto
            NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, ref);

            System.out.println(name + " registrado en el Naming Service.");
            // Deja corriendo el ORB para recibir solicitudes
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fallo al configurar el ORB");
        }
    }
}
