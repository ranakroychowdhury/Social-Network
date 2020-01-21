package Servlets;

import Utilities.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

/**
 * Created by ASUS on 12/20/2016.
 */
@WebServlet(name = "ImageServletProcess", urlPatterns = "/ImageServletProcess.do")
public class ImageServletProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] buffer = new byte[10240];
        String id = request.getParameter("id");
        DatabaseConnection db = new DatabaseConnection();
        InputStream input = db.getPicById(id);
        OutputStream output = response.getOutputStream();
        response.setContentType("image/gif");
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            output.write(buffer, 0, length);
        }
    }
}
