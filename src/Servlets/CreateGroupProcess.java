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
@WebServlet(name = "CreateGroupProcess", urlPatterns = "/CreateGroupProcess.do")
public class CreateGroupProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupName = request.getParameter("groupName");
        String descritpion = request.getParameter("description");
        String creatorId = request.getParameter("creatorId");
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        int count = db.createGroup(groupName,descritpion,creatorId);
        if(count>0){
            String groupId = Integer.toString(count);
            String addMembers = "addMembers";
            session.setAttribute("addMembers",addMembers);
            session.setAttribute("groupId", groupId);
            RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
            rd.forward(request, response);
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
