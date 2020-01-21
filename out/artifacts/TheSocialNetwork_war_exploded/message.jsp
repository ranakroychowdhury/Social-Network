<%@ page import="Models.Person" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.MessageThread" %>
<%@ page import="Utilities.DatabaseConnection" %>
<%@ page import="Models.Messages" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/12/2016
  Time: 11:08 AM
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
        String threadId = (String) session.getAttribute("threadId");
        String creating = (String) session.getAttribute("creatingThread");
        out.println("<div class=\"container-fluid\">");
        out.println("<div class=\"row\">\n" +
                "        <form class=\"well form-horizontal\" action=\"NewThreadProcess.do\" method=\"post\">\n" +
                "            <div class=\"form-group\">\n" +
                "                <label class=\"col-md-4 control-label\">Thread Name :</label>\n" +
                "                <div class=\"col-md-4\">\n" +
                "                    <input type=\"text\" name=\"threadName\" placeholder=\"enter thread name\">\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"form-group\">\n" +
                "                <label class=\"col-md-4 control-label\"></label>\n" +
                "                <div class=\"col-md-4\">\n" +
                "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Start New Thread\">\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </form>\n" +
                "    </div>");
        if(threadId != null && creating!=null && creating.compareTo("creatingThread")==0){
            ArrayList<Person> friendsToMessage = (ArrayList<Person>)session.getAttribute("friendsToMessage");

            if(friendsToMessage != null){
                DatabaseConnection db = new DatabaseConnection();
                for(Person friend: friendsToMessage){
                    if(!db.isFriendInThread(friend.getUserId(), threadId)){
                        out.println("<div class=\"row well\">\n" +
                                "    <div class=\"col-md-2\">\n" +
                                "        <img src=\"ImageServletProcess.do?id="+friend.getProfilePicId()+"\"\n" +
                                "             class=\"img-circle img-responsive\" width=\"60%\" />\n" +
                                "    </div>\n" +
                                "    <div class=\"col-md-10\">\n" +
                                "        <div class=\"pull-left\"><b>"+friend.getUserName()+"</b>\n" +
                                "        </div>\n" +
                                "        <form class=\"form-horizontal pull-right\" action=\"AddFriendsMessageProcess.do\" method=\"post\" >\n" +
                                "            <div class=\"form-group\">\n" +
                                "                <label class=\"col-md-4 control-label\"></label>\n" +
                                "                <div class=\"col-md-4\">\n" +
                                "                    <input type=\"hidden\" name=\"threadId\" value="+threadId+">\n" +
                                "                    <input type=\"hidden\" name=\"friendId\" value="+friend.getUserId()+">\n" +
                                "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Add\">\n" +
                                "                </div>\n" +
                                "            </div>\n" +
                                "        </form>\n" +
                                "    </div>\n" +
                                "</div>");
                    }

                }
                db.close();
                session.removeAttribute("friendsToMessage");
            }
            else{

                out.println("<h1>No friends to add</h1>");


            }
            session.removeAttribute("threadId");
            session.removeAttribute("creatingThread");
        }
        else {
            out.println("<div class=\"row-fluid\">\n" +
                    "    <div class=\"col-md-4 text-center pull-left\" >\n" +
                    "        <h3 class=\"text-primary well\">Message Threads</h3>");
            DatabaseConnection db = new DatabaseConnection();
            ArrayList<MessageThread> threads = db.getThreadsById(currUser.getUserId());
            if(threads != null && !threads.isEmpty()){
                for (MessageThread thread : threads){
                    out.println("<div class=\"well\">\n" +
                            "            <form class=\"form-horizontal\" action=\"ThreadMessageShowProcess.do\" method=\"post\">\n" +
                            "                <div class=\"form-group\">\n" +
                            "                    <div class=\"col-md-4\">\n" +
                            "                        <input type=\"hidden\" name=\"threadId\" value="+thread.getThreadId()+">\n" +
                            "                        <input type=\"hidden\" name=\"threadName\" value="+thread.getThreadName()+">\n" +
                            "                        <input type=\"submit\" class=\"btn btn-warning\" value="+thread.getThreadName()+">\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </form>\n" +
                            "        </div>");
                }

                session.removeAttribute("threads");
            }
            db.close();
            out.println("</div>");
            MessageThread currThread = (MessageThread)session.getAttribute("currThread");
            out.println("<div class=\"col-md-8 pull-left\"  >");
            if(currThread != null){
                ArrayList<Person> friendsInThread = currThread.getParticipants();
                ArrayList<Messages> messagesInThread = currThread.getMessages();
                if(friendsInThread != null && !friendsInThread.isEmpty()){
                    out.println("<div class=\"row well\">");
                    for(Person friend: friendsInThread){
                        out.println("<h4>"+friend.getUserName()+"</h4>");
                    }
                    out.println("</div>");
                }
                if(messagesInThread!=null && !messagesInThread.isEmpty()){

                    for(Messages message : messagesInThread){
                        out.println("<div class=\"row well\" style=\"border: 1px solid gainsboro\">\n" +
                                "            <div class=\"col-md-2\">\n" +
                                "                <img src=\"ImageServletProcess.do?id="+message.getSender().getProfilePicId()+"\" alt=\"The pro pic\"\n" +
                                "                     class=\"img-circle img-responsive\" width=\"80%\" />\n" +
                                "            </div>\n" +
                                "            <div class=\"col-md-6\">\n" +
                                "                <div><b>"+message.getSender().getUserName()+"</b>\n" +
                                "                </div>\n" +
                                "                <div>"+message.getContents()+"</div>\n" +
                                "\n" +
                                "            </div>\n" +
                                "        </div>");
                    }

                }
                out.println("<form class=\"well form-horizontal\" action=\"CreateMessageProcess.do\" method=\"post\" >\n" +
                        "            <div class=\"form-group\">\n" +
                        "\n" +
                        "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                        "                    <div class=\"input-group\">\n" +
                        "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-pencil\"></i></span>\n" +
                        "                        <textarea class=\"form-control\" name=\"messageContent\" placeholder=\"write a message.\"></textarea>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <div class=\"form-group pull-right\">\n" +
                        "\n" +
                        "                <div class=\"col-md-4\">\n" +
                        "                    <input type=\"hidden\" name=\"threadId\" value="+currThread.getThreadId()+">\n" +
                        "                    <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                        "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Send\">\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </form>");
                session.removeAttribute("currThread");
            }
            //out.println("</div>");
            out.println(" </div>");
        }



        out.println("</div>");
    }
%>

<%--
<div class="row well">
    <div class="col-md-2">
        <img src="media/bleach.jpg"
             class="img-circle img-responsive" width="60%" />
    </div>
    <div class="col-md-10">
        <div class="pull-left"><b>friend.getUserName()</b>
        </div>
        <form class="form-horizontal pull-right" action="AddFriendsMessageProcess.do" method="post"  id="contact_form">
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="hidden" name="threadId" value="threadId">
                    <input type="hidden" name="friendId" value="friendId">
                    <input type="submit" class="btn btn-warning" value="Add">
                </div>
            </div>
        </form>
    </div>
</div>





<div class="container-fluid">
    <div class="row">
        <form class="well form-horizontal" action="NewThreadProcess.do" method="post">
            <div class="form-group">
                <label class="col-md-4 control-label">Thread Name :</label>
                <div class="col-md-4">
                    <input type="text" name="threadName" placeholder="enter thread name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="submit" class="btn btn-warning" value="Start New Thread">
                </div>
            </div>
        </form>
    </div>
</div>



<div class="row-fluid">
    <div class="col-md-4 col-md-offset-0 text-center" >
        <h3 class="text-primary well">Message Threads</h3>
        <div class="row well">
            <form class="form-horizontal" action="ThreadMessageShowProcess.do" method="post">
                <div class="form-group">
                    <div class="col-md-4">
                        <input type="hidden" name="threadId" value = "threadId">
                        <input type="submit" class="btn btn-warning" value="threadName">
                    </div>
                </div>
            </form>
        </div>

    </div>
    <div class="col-md-8 col-md-offset-5 pull-left" style="border: 1px solid gainsboro" >




    </div>
</div>--%>
</body>

<jsp:include page="foot.jsp" />
