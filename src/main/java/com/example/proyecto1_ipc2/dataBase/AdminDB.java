package com.example.proyecto1_ipc2.dataBase;

import com.example.proyecto1_ipc2.conexion.Conexion;
import com.example.proyecto1_ipc2.modelo.Punto_Control;
import com.example.proyecto1_ipc2.modelo.Ruta;
import com.example.proyecto1_ipc2.modelo.Usuario;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDB {

    public void guardarUsuarios(Usuario operador, Conexion conexion) {
        String query = "INSERT INTO usuarios(nombre,rol,contrasena,estado,nombre_usuario, precio_Xlibra) VALUES(?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conexion.connection.prepareStatement(query)) {
            stmt.setString(1, operador.getNombre());
            stmt.setInt(2, operador.getRol());
            stmt.setString(3, operador.getPassword());
            stmt.setBoolean(4, operador.isEstado());
            stmt.setString(5, operador.getNombreUsuario());
            stmt.setDouble(6, operador.getPrecioXLibra());
            stmt.executeUpdate();
            stmt.close();
            System.out.println("hecho");
        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e);
        }
    }

    public void actualizarUsuarios(Usuario usuario, Conexion conexion, int id) {
        String query = "update usuarios set nombre =?, rol=?,  contrasena=?, " +
                "estado=?, nombre_usuario=?, precio_Xlibra=? WHERE id_usuario = ?;";
        try (PreparedStatement stmt = conexion.connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setInt(2, usuario.getRol());
            stmt.setString(3, usuario.getPassword());
            stmt.setBoolean(4, usuario.isEstado());
            stmt.setString(5, usuario.getNombreUsuario());
            stmt.setDouble(6, usuario.getPrecioXLibra());
            stmt.setInt(7, id);
            stmt.executeUpdate();
            stmt.close();
            System.out.println("hecho");
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e);
        }
    }

    public void mostrarUsuarios(Conexion conexion, int id) {
        String query;
        if (id < 0) {
            query = "select * from usuarios";
        } else {
            query = "select * from usuarios where id_usuario=" + id;
        }

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los usuarios:" + e);
        }
    }

    public void eliminarusuarios(int id, Conexion conexion) {
        String query = "delete from usuarios WHERE id_usuario = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar usuarios: " + e);
        }
    }

    public void guardarPuntoControl(Punto_Control puntoControl, Conexion conexion) {
        String query = "INSERT INTO punto_control(tarifa,id_usuario, limite_cola) VALUES(?, ?, ?);";
        try (PreparedStatement stmt = conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, puntoControl.getTarifa());
            stmt.setInt(2, puntoControl.getIdUsuarioOperador());
            stmt.setInt(3, puntoControl.getLimiteCola());

            stmt.executeUpdate();
            stmt.close();
            System.out.println("hecho");
        } catch (SQLException e) {
            System.out.println("Error al guardar punto de Control: " + e);
        }
    }

    public void actualizarPuntoControl(Conexion conexion, Punto_Control punto_control, int id) {
        String query = "update punto_control set  tarifa=?, id_usuario=?, limite_cola=? WHERE id = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setInt(1, punto_control.getTarifa());
            stmt.setInt(2, punto_control.getIdUsuarioOperador());
            stmt.setInt(3, punto_control.getLimiteCola());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error al actualizar punto de control: " + e);
        }
    }

    public void mostrarPuntosControl(Conexion conexion, int id) {
        String query;
        if (id < 0) {
            query = "select * from punto_control";
        } else {
            query = "select * from punto_control where id=" + id;
        }

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los puntos de control:" + e);
        }
    }

    public void eliminarPuntoControl(int id, Conexion conexion) {
        String query = "delete from punto_control WHERE id = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar punto de control: " + e);
        }
    }

    public void guardarRuta(Ruta ruta, Conexion conexion) {
        String query = "INSERT INTO ruta (disponible,estado,id_usuario, id_punto_control, limite_cola) VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conexion.connection.prepareStatement(query)) {
            stmt.setBoolean(1, ruta.isDisponible());
            stmt.setBoolean(2, ruta.isEstado());
            stmt.setInt(3, ruta.getIdUsuario());
            stmt.setInt(4, ruta.getIdPuntoControl());
            stmt.setInt(5, ruta.getLimiteCola());

            stmt.executeUpdate();
            stmt.close();
            System.out.println("hecho");
        } catch (SQLException e) {
            System.out.println("Error al guardar ruta: " + e);
        }
    }

    public void actualizarRuta(Conexion conexion, Ruta ruta, int id) {
        String query = "update ruta set disponible =?, estado=?,  id_usuario=?, id_punto_control=?, limite_cola=? WHERE id_ruta = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setBoolean(1, ruta.isDisponible());
            stmt.setBoolean(2, ruta.isEstado());
            stmt.setInt(3, ruta.getIdUsuario());
            stmt.setInt(4, ruta.getIdPuntoControl());
            stmt.setInt(5, ruta.getLimiteCola());
            stmt.setInt(6, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error al actualizar la ruta: " + e);
        }
    }

    public void mostrarRuta(Conexion conexion, int id) {
        String query;
        if (id < 0) {
            query = "select * from ruta";
        } else {
            query = "select * from ruta where id_ruta=" + id;
        }

        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar la ruta:" + e);
        }
    }

    public void eliminarRuta(int id, Conexion conexion) {
        String query = "delete from ruta WHERE id_ruta = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error al eliminar ruta: " + e);
        }
    }

    //METODO PARA VERIFICAR SI LA COLA DEL PC TIENE ESPACIO DISPONIBLE
    public void traerCantiadPaqueteEnColaPuntoControl(int idPuntoControl, Conexion conexion) {
        String query = "SELECT COUNT(*) AS total_tuplas FROM paquete WHERE estado = 3 AND id_punto_control = ?;";
        try {
            PreparedStatement stmt = conexion.getConnection().prepareStatement(query);
            stmt.setInt(1, idPuntoControl);
            conexion.setResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("Error al consultar los cantidad de paquetes en punto de control:" + e);
        }
    }

}
