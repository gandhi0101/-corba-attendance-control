package utils;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

public class ORBSetup {
    public static void startORB(String[] args, Object servant, String name) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servant);
            NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            NameComponent[] path = ncRef.to_name(name);
            ncRef.rebind(path, ref);

            System.out.println(name + " está listo y esperando.");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
