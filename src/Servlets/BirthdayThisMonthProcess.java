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
 * Created by ASUS on 12/19/2016.
 */
@WebServlet(name = "BirthdayThisMonthProcess", urlPatterns = "/BirthdayThisMonthProcess.do")
public class BirthdayThisMonthProcess extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Person> birthdaysThisMonth = db.getBirthdaysThisMonth(userId);
        if(birthdaysThisMonth!=null && !birthdaysThisMonth.isEmpty()){
            HttpSession session = request.getSession();
            session.setAttribute("birthdaysThisMonth",birthdaysThisMonth);
            session.setAttribute("birthday","birthday");
        }
        RequestDispatcher rd = request.getRequestDispatcher("friendList.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
