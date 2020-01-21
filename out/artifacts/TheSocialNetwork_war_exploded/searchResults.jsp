<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.Person" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/13/2016
  Time: 1:28 PM
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

    ArrayList<Person> persons = (ArrayList<Person>) session.getAttribute("searchedPersons");
    if(persons == null){
        out.println("No user with such name found.");
    }
    else if(persons.isEmpty()){
        out.println("No user with such name found.");
    }
    else{
        out.println("<div class=\"container\">");
        for(Person person: persons){
            out.println("<div class=\"row\">\n" +
                    "    <div class=\"col-md-2\">\n" +
                    "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                    "             class=\"img-circle\" width=\"100%\" />\n" +
                    "    </div>\n" +
                    "    <div class=\"well col-md-10\">\n" +
                    "        <div><h1>"+person.getUserName()+"</h1>\n" +
                    "            <div class=\"pull-right muted\"><small>"+person.getFriendStatus()+"</small></div>\n" +
                    "        </div>\n" +
                    "        <div>Username :"+person.getUserName()+" </div>\n" +
                    "        <div>Full Name : "+person.getFirstName()+"&nbsp;"+person.getLastName()+"</div>\n");
            if(person.getUserId().compareTo(currUser.getUserId()) == 0){
                out.println("<h2>Oh!! It's you</h2>");
            }
            else if(person.getFriendStatus().compareTo("Friend") == 0){
                out.println("<h3>Already friend.</h3>");
            }
            else if(person.getFriendReqStatus().compareTo("pending") == 0 || person.getFriendReqStatus().compareTo("rejected") == 0){
                out.println("<h2>See friend request page</h2>");
            }
            else {
                out.println("        <form class=\"form-horizontal\" action=\"FriendRequestProcess.do\" method=\"post\"  id=\"contact_form\">\n" +
                        "            <div class=\"form-group\">\n" +
                        "                <label class=\"col-md-4 control-label\"></label>\n" +
                        "                <div class=\"col-md-4\">\n" +
                        "                    <input type=\"hidden\" name=\"personId\" value=" + person.getUserId() + ">\n" +
                        "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Send Friend Request\">\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </form>\n");
            }
            out.println("    </div>\n" +
                    "</div>\n");
        }
        out.println("</div>");

    }


%>


<%--
<div class="row">
    <div class="col-md-2">
        <img src="media/bleach.jpg"
             class="img-circle" width="100%" />
    </div>
    <div class="col-md-10">
        <div><b>Saiful Islam</b>
            <div class="pull-right muted"><small>friend or not </small></div>
        </div>
        <div>Username : </div>
        <div>Full Name : </div>
        <form class="well form-horizontal" action="FriendRequestProcess.do" method="post"  id="contact_form">
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="submit" class="btn btn-warning" value="Send Friend Request">
                </div>
            </div>
        </form>
    </div>
</div>
--%>
</body>

<jsp:include page="foot.jsp" />
