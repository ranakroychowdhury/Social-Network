<%@ page import="Models.Person" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.Posts" %>
<%@ page import="Models.Comments" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/14/2016
  Time: 5:27 PM
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
        ArrayList<Posts> sharedPosts = (ArrayList<Posts>)session.getAttribute("sharedPosts");
        if(sharedPosts != null){
            if(sharedPosts.isEmpty()){
                out.println("<h1>No shared memories</h1>");
            }
            else {
                out.println("<div class=\"container\"");
                for (Posts post : sharedPosts) {
                    out.println("<div class=\"row\">\n" +
                            "    <div class=\"col-md-2\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+post.getPosterPicId()+"\" alt=\"The pro pic\"\n" +
                            "             class=\"img-circle img-responsive\" width=\"80%\" />\n" +
                            "    </div>" +
                            "<h4 class=\"pull-right\">Like Count : " + post.getLikes() + "</h4>&nbsp;" +
                            "    <div class=\"col-md-10\">\n" +
                            "        <div><b>" + post.getPosterName() + "</b>\n" +
                            "        </div>\n" +
                            "        <div>" + post.getContents() + "</div><br><br>\n");
                    ArrayList<Comments> coms = post.getComments();
                    if (coms == null || coms.isEmpty()) {
                        out.println("");
                    } else {
                        //out.println("<div class=\"row\">");
                        for (Comments comment : coms) {
                            out.println("<div class=\"row\">\n" +
                                    "<div class=\"col-md-2 pull-left\" >\n" +
                                    "    <img src=\"ImageServletProcess.do?id="+comment.getCommenterPicId()+"\" alt=\"The pro pic\"\n" +
                                    "         class=\"img-circle\" width=\"70%\" />\n" +
                                    "</div>\n" +
                                    "<div class=\"col-md-6\">\n" +
                                    "    <div><b>" + comment.getCommenterName() + "</b>\n" +
                                    "    </div>\n" +
                                    "    <div>" + comment.getContents() + "</div>\n" +
                                    "<h4 class=\"pull-left\">Like Counts : " + comment.getLikes() + "</h4>" +
                                    "</div></div><br><br>\n");
                        }
                        out.println("</div>");
                    }


                }
                out.println("</div>");
            }
            session.removeAttribute("sharedPosts");
        }
        else{
            ArrayList<Person> friends = (ArrayList<Person>) session.getAttribute("friends");
            out.println("<div class = \"container\">");
            out.println("<div class=\"row well center-block\">\n" +
                    "    <form class=\"form-horizontal\" action=\"BirthdayThisMonthProcess.do\" method=\"post\">\n" +
                    "        <div class=\"form-group\">\n" +
                    "            <label class=\"col-md-4 control-label\"></label>\n" +
                    "            <div class=\"col-md-4\">\n" +
                    "                <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                    "                <input type=\"submit\" class=\"btn btn-warning\" value=\"Birthdays This Month\">\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </form>\n" +
                    "\n" +
                    "</div>");


            ArrayList<Person>birthdaysThisMonth = (ArrayList<Person>)session.getAttribute("birthdaysThisMonth");
            String birthday = (String)session.getAttribute("birthday");
            if(birthday !=null){
                out.println("<div class = \"text-center\">");

                out.println("<h1 class=\"text-primary\">Have birthdays this Month</h1>");
                out.println("</div>");
                if(birthdaysThisMonth!=null && !birthdaysThisMonth.isEmpty()){
                    for (Person person : birthdaysThisMonth) {
                        out.println("<div class=\"row\">\n" +
                                "    <div class=\"col-md-2\">\n" +
                                "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                                "             class=\"img-circle\" width=\"100%\" />\n" +
                                "    </div>\n" +
                                "    <div class=\"col-md-10\">\n" +
                                "        <div><h1>" + person.getUserName() + "</h1>\n" +
                                "        </div>\n" +
                                "        <div>BirthDate :" + person.getBirthDay() + " </div>\n" +
                                "        <div>BirthMonth : " + person.getBirthMonth() +  "</div>\n" +
                                "    </div>\n" +
                                "</div><br><br>\n");
                    }
                    session.removeAttribute("birthdaysThisMonth");
                }
                else{
                    out.println("<div class=\"text-center\"><h2>No one has birthday this month.</h2></div>");
                }
                session.removeAttribute("birthday");
            }



            out.println("<div class = \"text-center\">");

            out.println("<h1 class=\"text-primary\">Friend List</h1>");
            out.println("</div>");

            if(friends.isEmpty()){
                out.println("<h3>You have no friends yet.</h3>");
            }
            else {
                for (Person person : friends) {
                    out.println("<div class=\"row\">\n" +
                            "    <div class=\"col-md-2\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                            "             class=\"img-circle\" width=\"100%\" />\n" +
                            "    </div>\n" +
                            "    <div class=\"col-md-10\">\n" +
                            "        <div><h1>" + person.getUserName() + "</h1>\n" +
                            "            <div class=\"pull-right muted\"><small>" + person.getFriendStatus() + "</small></div>\n" +
                            "        </div>\n" +
                            "        <div>Username :" + person.getUserName() + " </div>\n" +
                            "        <div>Full Name : " + person.getFirstName() + "&nbsp;" + person.getLastName() + "</div>\n" +
                            "<form class=\"form-horizontal\" action=\"SharedHistoryProcess.do\" method=\"post\">\n" +
                            "        <div class=\"form-group\">\n" +
                            "            <label class=\"col-md-4 control-label\"></label>\n" +
                            "            <div class=\"col-md-4\">\n" +
                            "                <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                            "                <input type=\"hidden\" name=\"friendId\" value="+person.getUserId()+">\n" +
                            "                <input type=\"submit\" class=\"btn btn-warning\" value=\"See Shared History\">\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </form>"+
                            "    </div>\n" +
                            "</div><br><br>\n");
                }
                out.println("</div>");
            }
        }

    }
%>
<%--
<div class="row well center-block">
    <form class="form-horizontal" action="BrthdayThisMonthProcess.do" method="post">
        <div class="form-group">
            <label class="col-md-4 control-label"></label>
            <div class="col-md-4">
                <input type="hidden" name="userId" value="currUser.getUserId()">
                <input type="submit" class="btn btn-warning" value="Birthdays This Month">
            </div>
        </div>
    </form>

</div>
--%>
</body>

<jsp:include page="foot.jsp" />
