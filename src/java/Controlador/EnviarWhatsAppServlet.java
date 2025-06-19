package Controlador;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EnviarWhatsAppServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("name");
        String numero = request.getParameter("email").replaceAll(" ", ""); // WhatsApp sin espacios
        String mensaje = request.getParameter("message");

        String texto = "Hola " + nombre + ", gracias por contactarnos. Esta es tu consulta enviada : " + mensaje;

        String instanceId = "instance126835";
        String token = "AQUI_TU_TOKEN";  // ← reemplaza por tu token real
        String apiUrl = "https://api.ultramsg.com/" + instanceId + "/messages/chat";

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        String postData = "token=" + token +
                          "&to=" + URLEncoder.encode(numero, "UTF-8") +
                          "&body=" + URLEncoder.encode(texto, "UTF-8");

        try (OutputStream os = con.getOutputStream()) {
            os.write(postData.getBytes());
        }

        int status = con.getResponseCode();
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (status == 200) {
                out.println("<script>alert('✅ Mensaje enviado a WhatsApp'); window.location='index.jsp';</script>");
            } else {
                out.println("<script>alert('❌ Error al enviar mensaje. Código: " + status + "'); window.location='index.jsp';</script>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet que envía mensajes a WhatsApp usando UltraMsg";
    }
}
