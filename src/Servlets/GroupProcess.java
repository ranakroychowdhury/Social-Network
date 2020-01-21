package Servlets;

import Models.Group;
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
 * Created by ASUS on 12/19/2016.
 */
@WebServlet(name = "GroupProcess", urlPatterns = "/GroupProcess.do")
public class GroupProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("currUser");
        String userId = user.getUserId();
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Group> myGroups = db.getGroupsByUser(userId);
        if(myGroups!=null & !myGroups.isEmpty()){
            session.setAttribute("myGroups", myGroups);
        }
        RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
        rd.forward(request, response);
    }
}
