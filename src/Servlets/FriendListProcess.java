package Servlets;

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
 * Created by ASUS on 12/15/2016.
 */
@WebServlet(name = "FriendListProcess", urlPatterns = "/FriendListProcess.do")
public class FriendListProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("currUser");
        String id = user.getUserId();
        ArrayList<Person> friends = db.getFriendLIst(id);
        db.close();
        if(friends != null){
            session.setAttribute("friends", friends);
            RequestDispatcher rd = request.getRequestDispatcher("friendList.jsp");
            rd.forward(request, response);
        }
        else{
            String message = "You have no friends.";
            //HttpSession session = request.getSession();
            session.setAttribute("Message", message);
            RequestDispatcher rd = request.getRequestDispatcher("friendList.jsp");
            rd.forward(request, response);
        }
    }
}
