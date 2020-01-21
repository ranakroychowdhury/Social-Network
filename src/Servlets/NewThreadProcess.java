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
@WebServlet(name = "NewThreadProcess", urlPatterns = "/NewThreadProcess.do")
public class NewThreadProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String threadName = request.getParameter("threadName");
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("currUser");
        String userId = user.getUserId();
        int thread = db.createNewThread(threadName,userId);
        if(thread >= 0){

            String threadId = Integer.toString(thread);
            session.setAttribute("threadId", threadId);
            session.setAttribute("creatingThread","creatingThread");
            ArrayList<Person> friends = db.getFriendLIst(userId);
            session.setAttribute("friendsToMessage", friends);
            db.close();
            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
            rd.forward(request, response);
        }
        else{
            db.close();
            RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
