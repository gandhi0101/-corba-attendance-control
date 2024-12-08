package server;

import Asistencia.UsuarioServicePOA;
import java.util.HashSet;

public class UserServiceImpl extends UsuarioServicePOA {
    private HashSet<String> usuarios = new HashSet<>();

    public UserServiceImpl() {
        usuarios.add("Gandhi");
        usuarios.add("Arturo");
        usuarios.add("Juan");
        usuarios.add("Maria");
        usuarios.add("Pedro");
        usuarios.add("Jose");
        usuarios.add("Ana");
    }

    @Override
    public boolean validarUsuario(String idUsuario) {
        return usuarios.contains(idUsuario);
    }
}
