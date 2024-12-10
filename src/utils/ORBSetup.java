package utils;

import org.omg.CORBA.ORB;

public class ORBSetup {
    public static ORB initializeORB(String[] args) {
        return ORB.init(args, null);
    }
}
