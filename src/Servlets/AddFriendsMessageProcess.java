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
 * Created by ASUS on 12/18/2016.
 */
@WebServlet(name = "AddFriendsMessageProcess", urlPatterns = "/AddFriendsMessageProcess.do")
public class AddFriendsMessageProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String threadId = request.getParameter("threadId");
        String friendId = request.getParameter("friendId");
        DatabaseConnection db = new DatabaseConnection();
        System.out.println(threadId+"\n\n"+friendId);
        int count = db.addFriendsToThread(threadId,friendId);
        if(count > 0){
            HttpSession session = request.getSession();

            session.setAttribute("threadId", threadId);
            Person user = (Person)session.getAttribute("currUser");
            session.setAttribute("creatingThread","creatingThread");
            String userId = user.getUserId();
            ArrayList<Person> friends = db.getFriendLIst(userId);
            session.setAttribute("friendsToMessage", friends);
            db.close();
            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
            rd.forward(request, response);
        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("threadId", threadId);
            Person user = (Person)session.getAttribute("currUser");
            session.setAttribute("creatingThread","creatingThread");
            String userId = user.getUserId();
            System.out.println("didn't add \n\n\nfriend to thread");
            ArrayList<Person> friends = db.getFriendLIst(userId);
            session.setAttribute("friendsToMessage", friends);
            db.close();
            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
