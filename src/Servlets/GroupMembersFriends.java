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
@WebServlet(name = "GroupMembersFriends", urlPatterns = "/GroupMembersFriends.do")
public class GroupMembersFriends extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String userId = request.getParameter("userId");
        String groupName = request.getParameter("groupName");
        String description = request.getParameter("description");
        String creatorId = request.getParameter("creatorId");
        DatabaseConnection db =new DatabaseConnection();
        ArrayList<Person> friends = db.friendsInGroup(userId,groupId);
        Group currGroup = new Group(groupId,groupName,description,creatorId);
        HttpSession session = request.getSession();
        if(friends!=null && !friends.isEmpty()){
            currGroup.setMember(friends);
        }
        session.setAttribute("currGroup", currGroup);

        RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
