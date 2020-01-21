package Servlets;

import Models.Album;
import Models.Person;
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
@WebServlet(name = "AlbumProcess", urlPatterns = "/AlbumProcess.do")
public class AlbumProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person currUser =(Person) session.getAttribute("currUser");
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Album> albums = db.getAlbumsByUser(currUser.getUserId());
        if(albums!=null){
            session.setAttribute("myAlbums",albums);
        }
        RequestDispatcher rd = request.getRequestDispatcher("albums.jsp");
        rd.forward(request, response);

    }
}
