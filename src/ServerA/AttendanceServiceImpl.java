package serverA;

import AsistenciaModule.AttendanceServicePOA;
import org.omg.CORBA.ORB;

import java.util.HashMap;

public class AttendanceServiceImpl extends AttendanceServicePOA {
    private ORB orb;
    private HashMap<String, Boolean> attendance = new HashMap<>();

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public boolean markAttendance(String userId, String password) {
        // Simple validación
        attendance.put(userId, true);
        return true;
    }

    @Override
    public boolean isPresent(String userId) {
        return attendance.getOrDefault(userId, false);
    }
}
