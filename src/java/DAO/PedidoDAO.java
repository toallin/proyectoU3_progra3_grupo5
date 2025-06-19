/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
   private Connection conexion;

    public PedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

   public Pedido obtenerUltimoPedidoPagado(int idUsuario) throws SQLException {
    String sql = """
        SELECT p.*, pg.monto_total
        FROM pedidos p
        JOIN pagos pg ON pg.pedido_id = p.id
        WHERE p.usuario_id = ? AND pg.estado = 'Pagado'
        ORDER BY pg.fecha_pago DESC
        LIMIT 1
    """;

    Pedido pedido = null;

    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            pedido = new Pedido();
            pedido.setIdPedido(rs.getInt("id"));
            pedido.setIdUsuario(rs.getInt("usuario_id"));
            pedido.setFechaPedido(rs.getDate("fecha_pedido"));
            pedido.setEstado(rs.getString("estado"));
            pedido.setTotal(rs.getDouble("monto_total")); // viene desde tabla pagos
            pedido.setDetalles("Pedido pagado recientemente");
        }
    }
    return pedido;
}

 public List<Pedido> obtenerPedidosAnteriores(int idUsuario) throws SQLException {
    String sql = """
        SELECT p.*, pg.monto_total
        FROM pedidos p
        JOIN pagos pg ON pg.pedido_id = p.id
        WHERE p.usuario_id = ? AND pg.estado = 'Pagado'
        ORDER BY p.fecha_pedido DESC
    """;

    List<Pedido> pedidos = new ArrayList<>();

    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(rs.getInt("id"));
            pedido.setIdUsuario(rs.getInt("usuario_id"));
            pedido.setFechaPedido(rs.getDate("fecha_pedido"));
            pedido.setEstado(rs.getString("estado"));
            pedido.setTotal(rs.getDouble("monto_total")); // viene desde pagos
            pedido.setDetalles("Historial del pedido");
            pedidos.add(pedido);
        }
    }
    return pedidos;
}



    public void insertarPedido(Pedido pedido) throws SQLException {
    String sql = "INSERT INTO pedidos (id_usuario, fecha_pedido, estado, total, detalles) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, pedido.getIdUsuario());
        stmt.setTimestamp(2, new Timestamp(pedido.getFechaPedido().getTime()));
        stmt.setString(3, pedido.getEstado());
        stmt.setDouble(4, pedido.getTotal());
        stmt.setString(5, pedido.getDetalles());
        stmt.executeUpdate();
    }
}
    // Agrega esto en PedidoDAO.java
public boolean guardarPedido(Pedido pedido) throws SQLException {
    String sql = "INSERT INTO pedidos (id_usuario, fecha_pedido, estado, total, detalles) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, pedido.getIdUsuario());
        stmt.setTimestamp(2, new java.sql.Timestamp(pedido.getFechaPedido().getTime()));
        stmt.setString(3, pedido.getEstado());
        stmt.setDouble(4, pedido.getTotal());
        stmt.setString(5, pedido.getDetalles());
        return stmt.executeUpdate() > 0;
    }
}

}
