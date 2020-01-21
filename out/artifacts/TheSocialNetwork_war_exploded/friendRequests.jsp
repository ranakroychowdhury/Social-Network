<%@ page import="Models.Person" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Utilities.DatabaseConnection" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/14/2016
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="base.jsp" />
<body>
<jsp:include page="nav.jsp" />
<%
    Person currUser = (Person) session.getAttribute("currUser");
    //String id = (String) session.getAttribute("userID");
    if(currUser == null){
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
    else{
        String msg = (String) session.getAttribute("Message");
        if(msg != null){
            out.println("<h2>"+msg+"</h2>");
            session.removeAttribute("Message");
        }
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Person> sentFriendRequests = db.getSentFriendRequests(currUser.getUserId());
        ArrayList<Person> recievedFriendRequests = db.getRecievedFriendRequests(currUser.getUserId());
        out.println("<div class = \"container\">");
        out.println("<h1>Sent Requests</h1>");
        if(sentFriendRequests.isEmpty()){
            out.println("<h3>No Sent Requests</h3>");
        }
        else {
            for (Person person : sentFriendRequests) {
                out.println("<div class=\"row\">\n" +
                        "    <div class=\"col-md-2\">\n" +
                        "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                        "             class=\"img-circle\" width=\"100%\" />\n" +
                        "    </div>\n" +
                        "    <div class=\"col-md-10\">\n" +
                        "        <div><h1>" + person.getUserName() + "</h1>\n" +
                        "            <div class=\"pull-right muted\"><small>" + person.getFriendReqStatus() + "</small></div>\n" +
                        "        </div>\n" +
                        "        <div>Username :" + person.getUserName() + " </div>\n" +
                        "        <div>Full Name : " + person.getFirstName() + "&nbsp;" + person.getLastName() + "</div>\n" +
                        "    </div>\n" +
                        "</div><br><br>\n");
            }
        }


        out.println("<h1>Recieved Requests</h1>");
        if(recievedFriendRequests.isEmpty()){
            out.println("<h3>No recieved Requests</h3>");
        }
        else {
            for (Person person : recievedFriendRequests) {
                out.println("<div class=\"row\">\n" +
                        "    <div class=\"col-md-2\">\n" +
                        "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                        "             class=\"img-circle\" width=\"100%\" />\n" +
                        "    </div>\n" +
                        "    <div class=\"col-md-10\">\n" +
                        "        <div><h1>" + person.getUserName() + "</h1>\n" +
                        "            <div class=\"pull-right muted\"><small>" + person.getFriendReqStatus() + "</small></div>\n" +
                        "        </div>\n" +
                        "        <div>Username :" + person.getUserName() + " </div>\n" +
                        "        <div>Full Name : " + person.getFirstName() + "&nbsp;" + person.getLastName() + "</div>\n" +
                        "        <form class=\"well form-horizontal\" action=\"AcceptDenyReqProcess.do\" method=\"post\"  id=\"contact_form\">\n" +
                        "            <div class=\"form-group\">\n" +
                        "                <label class=\"col-md-4 control-label\"></label>\n" +
                        "                <div class=\"col-md-4\">\n" +
                        "                    <input type=\"hidden\" name=\"personId\" value=" + person.getUserId() + ">\n" +
                        "                    <input type=\"submit\" class=\"btn btn-warning \" name=\"action\" value=\"Accept\">\n" +
                        "                    <input type=\"submit\" class=\"btn btn-warning\" name=\"action\" value=\"Reject\">\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </form>\n" +
                        "    </div>\n" +
                        "</div><br><br>\n");
            }
        }
        out.println("</div>");
    }
%>

</body>

<jsp:include page="foot.jsp" />