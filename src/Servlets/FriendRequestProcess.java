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
 * Created by ASUS on 12/14/2016.
 */
@WebServlet(name = "FriendRequestProcess" , urlPatterns = "/FriendRequestProcess.do")
public class FriendRequestProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recieverId = request.getParameter("personId");
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("currUser");
        String senderId = user.getUserId();
        int count = db.sendFriendRequest(senderId, recieverId);

        //ArrayList<Person> persons = db.getUsersByName("%"+userName.toUpperCase()+"%", userId);
        db.close();

        if(count>0)
        {
            String message = "You have successfully sent a friend request.";
            //HttpSession session = request.getSession();
            session.setAttribute("Message", message);
            RequestDispatcher rd = request.getRequestDispatcher("friendRequests.jsp");
            rd.forward(request, response);
        }
        else
        {
            //String postNot = "Your post didn't get posted.";


            RequestDispatcher rd = request.getRequestDispatcher("friendRequests.jsp");

            rd.forward(request, response);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
