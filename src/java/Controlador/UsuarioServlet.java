package Controlador;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import Conexion.conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        Connection con = (Connection) getServletContext().getAttribute("conexion");
        if (con == null) {
            try {
                conexion c = new conexion();
                con = c.getConnection();
                getServletContext().setAttribute("conexion", con);
            } catch (Exception e) {
                throw new ServletException("Error al conectar a la base de datos", e);
            }
        }
        usuarioDAO = new UsuarioDAO(con);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("register".equalsIgnoreCase(accion)) {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("register".equalsIgnoreCase(accion)) {
            registrarUsuario(request, response);
        } else if ("login".equalsIgnoreCase(accion)) {
            loginUsuario(request, response);
        } else {
            response.sendRedirect("usuario");
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String direccion = request.getParameter("direccion");

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);
        usuario.setDireccion(direccion);

        try {
            if (usuarioDAO.obtenerPorEmail(email) != null) {
                request.setAttribute("error", "El email ya está registrado");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            boolean registrado = usuarioDAO.registrarUsuario(usuario);
            if (registrado) {
                // Asignar rol automáticamente
                usuarioDAO.asignarRolUsuario(usuario.getEmail(), "USUARIO");
                response.sendRedirect("usuario?accion=login");
            } else {
                request.setAttribute("error", "No se pudo registrar usuario");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error en registro", e);
        }
    }

    private void loginUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        try {
            Usuario usuario = usuarioDAO.obtenerPorEmail(email);
            if (usuario != null && usuario.getContrasena().equals(contrasena)) {
                if (usuarioDAO.tieneRol(usuario.getId(), "USUARIO")) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioId", usuario.getId());
                    session.setAttribute("usuarioNombre", usuario.getNombre());
                    session.setAttribute("usuarioEmail", usuario.getEmail()); // <-- Agrega esta línea
                    response.sendRedirect("dashboard");
                } else {
                    request.setAttribute("error", "Acceso no autorizado.");
                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Email o contraseña incorrectos");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error en login", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador para login y registro de usuarios";
    }
}
