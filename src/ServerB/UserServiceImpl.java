package serverB;

import AsistenciaModule.UserServicePOA;
import java.util.HashMap;

public class UserServiceImpl extends UserServicePOA {
    private HashMap<String, String> users = new HashMap<>();

    @Override
    public boolean registerUser(String userId, String password) {
        if (users.containsKey(userId)) {
            return false; // Usuario ya existe
        }
        users.put(userId, password);
        return true;
    }

    @Override
    public boolean authenticateUser(String userId, String password) {
        return users.getOrDefault(userId, "").equals(password);
    }
}
