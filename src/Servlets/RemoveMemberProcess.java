package Servlets;

import Models.Group;
import Models.Posts;
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
@WebServlet(name = "RemoveMemberProcess", urlPatterns = "/RemoveMemberProcess.do")
public class RemoveMemberProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String groupName = request.getParameter("groupName");
        String description = request.getParameter("description");
        String creatorId = request.getParameter("creatorId");
        String memberId = request.getParameter("memberId");
        DatabaseConnection db = new DatabaseConnection();
        HttpSession session = request.getSession();
        int count = db.removeGroupMember(memberId,groupId);
        Group currGroup = new Group(groupId,groupName,description,creatorId);
        ArrayList<Posts> groupPosts = db.showGroupPosts(groupId);
        if(groupPosts!=null) currGroup.setGroupPosts(groupPosts);

        session.setAttribute("currGroup", currGroup);

        RequestDispatcher rd = request.getRequestDispatcher("group.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
