package Servlets;

import Models.Event;
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
 * Created by ASUS on 12/16/2016.
 */
@WebServlet(name = "JoinEventProcess", urlPatterns = "/JoinEventProcess.do")
public class JoinEventProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventId = request.getParameter("eventId");
        String joinStatus = request.getParameter("joinStatus");
        HttpSession session = request.getSession();
        Person user = (Person)session.getAttribute("currUser");
        String userId = user.getUserId();
        DatabaseConnection db = new DatabaseConnection();
        int count = db.updateJoinStatus(eventId, userId, joinStatus);
        if(count>0){
            ArrayList<Event> eventsInvited = db.eventsInvitedTo(userId);
            ArrayList<Event> eventsCreated = db.eventsCreated(userId);
            db.close();
            if(eventsInvited != null && !eventsInvited.isEmpty()){
                session.setAttribute("eventsInvited", eventsInvited);
            }
            if(eventsCreated != null && !eventsCreated.isEmpty()){
                session.setAttribute("eventsCreated", eventsCreated);
            }
            RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
            rd.forward(request, response);
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("events.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
