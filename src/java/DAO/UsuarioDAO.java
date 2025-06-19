package DAO;

import Modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {
    private Connection con;

    public UsuarioDAO(Connection con) {
        this.con = con;
    }

    // Registrar usuario
   public boolean registrarUsuario(Usuario usuario) throws SQLException {
    Connection con = this.con;
    boolean registrado = false;
    String sqlUsuario = "INSERT INTO usuarios (nombre, email, contraseña, direccion) VALUES (?, ?, ?, ?)";
    String sqlRol = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (?, ?)";

    try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
        psUsuario.setString(1, usuario.getNombre());
        psUsuario.setString(2, usuario.getEmail());
        psUsuario.setString(3, usuario.getContrasena());
        psUsuario.setString(4, usuario.getDireccion());

        int filas = psUsuario.executeUpdate();
        if (filas > 0) {
            // Obtener ID generado del usuario
            try (ResultSet rs = psUsuario.getGeneratedKeys()) {
                if (rs.next()) {
                    int usuarioId = rs.getInt(1);
                    // Asignar rol USUARIO (rol_id=2)
                    try (PreparedStatement psRol = con.prepareStatement(sqlRol)) {
                        psRol.setInt(1, usuarioId);
                        psRol.setInt(2, 2); // rol_id 2 = USUARIO
                        psRol.executeUpdate();
                        registrado = true;
                    }
                }
            }
        }
    }
    return registrado;
}

public boolean tieneRol(int usuarioId, String rolNombre) throws SQLException {
    String sql = "SELECT COUNT(*) FROM usuario_rol ur "
               + "JOIN roles r ON ur.rol_id = r.id "
               + "WHERE ur.usuario_id = ? AND r.nombre = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, usuarioId);
        ps.setString(2, rolNombre);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}
// Asignar rol por nombre
public void asignarRolUsuario(String email, String nombreRol) throws SQLException {
    String getUsuarioIdSql = "SELECT id FROM usuarios WHERE email = ?";
    String getRolIdSql = "SELECT id FROM roles WHERE nombre = ?";
    String checkSql = "SELECT COUNT(*) FROM usuario_rol WHERE usuario_id = ? AND rol_id = ?";
    String insertSql = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (?, ?)";

    int usuarioId = -1;
    int rolId = -1;

    try (PreparedStatement ps = con.prepareStatement(getUsuarioIdSql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                usuarioId = rs.getInt("id");
            }
        }
    }

    try (PreparedStatement ps = con.prepareStatement(getRolIdSql)) {
        ps.setString(1, nombreRol);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                rolId = rs.getInt("id");
            }
        }
    }

    if (usuarioId != -1 && rolId != -1) {
        // Verificamos si ya existe la asignación
        try (PreparedStatement ps = con.prepareStatement(checkSql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, rolId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count == 0) {
                        try (PreparedStatement psInsert = con.prepareStatement(insertSql)) {
                            psInsert.setInt(1, usuarioId);
                            psInsert.setInt(2, rolId);
                            psInsert.executeUpdate();
                        }
                    } // si count > 0, ya existe asignación, no hacemos nada
                }
            }
        }
    } else {
        throw new SQLException("Usuario o rol no encontrado");
    }
}

    // Buscar usuario por email (para login o ver si existe)
    public Usuario obtenerPorEmail(String email) throws SQLException {
        String sql = "SELECT id, nombre, email, contraseña, direccion FROM usuarios WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setContrasena(rs.getString("contraseña"));
                    u.setDireccion(rs.getString("direccion"));
                    return u;
                } else {
                    return null;
                }
            }
        }
    }
}
