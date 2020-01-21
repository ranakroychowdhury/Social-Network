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
 * Created by ASUS on 12/21/2016.
 */
@WebServlet(name = "SeeCollaboratorsProcess", urlPatterns = "/SeeCollaboratorsProcess.do")
public class SeeCollaboratorsProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String albumId = request.getParameter("albumId");
        String albumName = request.getParameter("albumName");
        String creatorId = request.getParameter("creatorId");
        DatabaseConnection db =new DatabaseConnection();
        ArrayList<Person> persons = db.collaboratorInAlbum(albumId);
        Album currAlbum = new Album(albumId,albumName,creatorId);
        HttpSession session = request.getSession();
        if(persons!= null){
            currAlbum.setCollaborater(persons);
        }
        session.setAttribute("currAlbum", currAlbum);
        RequestDispatcher rd = request.getRequestDispatcher("albums.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
