package Controlador;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import Conexion.conexion;
import java.sql.*;
import Modelo.Plato;

public class SeleccionSemanalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        try {
            con = conexion.getConnection();
            Map<String, List<Plato>> platosPorTipo = new HashMap<>();
            String[] categorias = {"Desayuno", "Almuerzo", "Cena"};
            for (String cat : categorias) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM platos WHERE categoria = ?");
                ps.setString(1, cat);
                ResultSet rs = ps.executeQuery();
                List<Plato> lista = new ArrayList<>();
                while (rs.next()) {
                    Plato p = new Plato();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    lista.add(p);
                }
                platosPorTipo.put(cat, lista);
                rs.close();
                ps.close();
            }

            List<String> dias = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1); // mañana
            String[] nombresDias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
            for (int i = 0; i < 7; i++) {
                String nombreDia = nombresDias[calendar.get(Calendar.DAY_OF_WEEK) % 7];
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH) + 1;
                int anio = calendar.get(Calendar.YEAR);
                String fecha = String.format("%s %02d/%02d/%d", nombreDia, dia, mes, anio);
                dias.add(fecha);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            request.setAttribute("dias", dias);
            request.setAttribute("platosPorTipo", platosPorTipo);
            request.getRequestDispatcher("seleccion-platos-semanal.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            if (con != null) try { con.close(); } catch (SQLException ignore) {}
        }
    }
}
