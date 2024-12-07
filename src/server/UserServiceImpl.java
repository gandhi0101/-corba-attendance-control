package server;

import Asistencia.UsuarioServicePOA;
import java.util.HashSet;

public class UserServiceImpl extends UsuarioServicePOA {
    private HashSet<String> usuarios = new HashSet<>();

    public UserServiceImpl() {
        usuarios.add("user1");
        usuarios.add("user2");
    }

    @Override
    public boolean validarUsuario(String idUsuario) {
        return usuarios.contains(idUsuario);
    }
}
