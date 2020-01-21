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
import java.io.PrintWriter;

/**
 * Created by ASUS on 12/5/2016.
 */
@WebServlet(name = "LoginProcess" , urlPatterns = "/LoginProcess.do")
public class LoginProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //PrintWriter out = response.getWriter();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        DatabaseConnection db = new DatabaseConnection();
        Person currUser = db.existUser(userName, password);
        //System.out.println("\n\n\n"+userID+"\n\n\n\n");
        if(currUser != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute("currUser", currUser);

            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
        }
        else
        {
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }


    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<h1>HAGHGHHGJ</h1>");
    }
}
