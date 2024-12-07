package server;

import Asistencia.AsistenciaServicePOA;
import java.util.HashMap;

public class AttendanceServiceImpl extends AsistenciaServicePOA {
    private HashMap<String, Integer> asistencias = new HashMap<>();

    @Override
    public boolean registrarAsistencia(String idUsuario) {
        asistencias.put(idUsuario, asistencias.getOrDefault(idUsuario, 0) + 1);
        return true;
    }

    @Override
    public String generarReporte(String idUsuario) {
        return "Usuario: " + idUsuario + ", Asistencias: " + asistencias.getOrDefault(idUsuario, 0);
    }
}
