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

/**
 * Created by ASUS on 12/14/2016.
 */
@WebServlet(name = "AcceptDenyReqProcess" , urlPatterns = "/AcceptDenyReqProcess.do")
public class AcceptDenyReqProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user1 = request.getParameter("personId");
        String aod = request.getParameter("action");
        System.out.println("hehehe\n\n"+aod);
        HttpSession session = request.getSession();
        Person per = (Person)session.getAttribute("currUser");
        String user2 = per.getUserId();
        DatabaseConnection db = new DatabaseConnection();
        int count = db.acceptDenyRequest(user1, user2, aod.toLowerCase());
        db.close();
        if(count >0){
            RequestDispatcher rd = request.getRequestDispatcher("friendRequests.jsp");
            rd.forward(request, response);
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("friendRequests.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
