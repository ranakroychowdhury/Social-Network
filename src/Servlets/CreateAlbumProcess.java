package Servlets;

import Models.Album;
import Utilities.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ASUS on 12/20/2016.
 */
@WebServlet(name = "CreateAlbumProcess",urlPatterns = "/CreateAlbumProcess.do")
public class CreateAlbumProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String albumName = request.getParameter("albumName");
        String userId = request.getParameter("userId");
        DatabaseConnection db =new DatabaseConnection();
        int id = db.createAlbum(albumName,userId);
            HttpSession session = request.getSession();
            ArrayList<Album> albums = db.getAlbumsByUser(userId);
            if(albums!=null) {
                session.setAttribute("myAlbums", albums);
            }
        RequestDispatcher rd = request.getRequestDispatcher("albums.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
