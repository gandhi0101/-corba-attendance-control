package utils;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

import java.util.Properties;

public class ORBSetup {
    public static void startORB(String[] args, Properties orbProps, Servant servant, String name) {
        try {
            // Mostrar las propiedades del ORB antes de inicializarlo
            System.out.println("Iniciando ORB con las siguientes propiedades:");
            orbProps.forEach((key, value) -> System.out.println(key + " = " + value));

            // Inicializa el ORB con las propiedades proporcionadas
            ORB orb = ORB.init(args, orbProps);
            System.out.println("ORB inicializado exitosamente.");

            // Mostrar información sobre el ORB después de inicializarlo
            System.out.println("Detalles del ORB:");
            System.out.println("DefaultFactoryManager: " + orb.getClass().getName());
            System.out.println("ORB ID: " + orb.toString());

            // Inicializa el RootPOA y activa el POAManager
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            System.out.println("POA activado.");

            // Convierte el Servant en un objeto CORBA
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servant);

            // Obtiene el Naming Service y registra el objeto
            NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, ref);

            System.out.println(name + " registrado en el Naming Service.");
            System.out.println("ORB corriendo, esperando solicitudes...");
            
            // Deja corriendo el ORB para recibir solicitudes
            orb.run();
        } catch (Exception e) {
            System.err.println("Fallo al configurar el ORB");
            e.printStackTrace();
            throw new RuntimeException("Fallo al configurar el ORB");
        }
    }
}
