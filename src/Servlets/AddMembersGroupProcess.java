package Servlets;

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
 * Created by ASUS on 12/19/2016.
 */
@WebServlet(name = "AddMembersGroupProcess",urlPatterns = "/AddMembersGroupProcess.do")
public class AddMembersGroupProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String groupId = request.getParameter("groupId");
        String memberType = request.getParameter("memberType");
        HttpSession session = request.getSession();
        DatabaseConnection db = new DatabaseConnection();
        int count = db.addMembersToGroup(userId,groupId,memberType);
        if(count>0){
            String addMembers = "addMembers";
            session.setAttribute("addMembers",addMembers);
            session.setAttribute("groupId", groupId);
            RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
            rd.forward(request, response);
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
