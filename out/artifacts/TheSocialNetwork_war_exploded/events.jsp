<%@ page import="Models.Person" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Utilities.DatabaseConnection" %>
<%@ page import="Models.Event" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/12/2016
  Time: 4:53 PM
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
        String createEvent = (String) session.getAttribute("createEvent");
        String inviteGuests = (String) session.getAttribute("inviteGuests");
        String goingEvents = (String) session.getAttribute("goingEvents");
        ArrayList<Event>evetsInMyCity = (ArrayList<Event>)session.getAttribute("eventsInMyCity");
        if(evetsInMyCity!= null){
            if(!evetsInMyCity.isEmpty()){
                for(Event event : evetsInMyCity) {

                    DatabaseConnection db = new DatabaseConnection();
                    event.setJoinStatus(db.getEventJoinStatus(event.getEventId(), currUser.getUserId()));
                    out.println("<div class = \"well row text-center\">\n" +
                            "            <h2 class=\"text-primary\">\n" +
                            "                Event Name : " + event.getEventName() + "\n" +
                            "            </h2>\n" +
                            "            <h3 class=\"\">\n" +
                            "                Event Type : " + event.getEventType() + "\n" +
                            "            </h3>\n" +
                            "            <h4 class=\"\">\n" +
                            "                Description : " + event.getDescription() + "\n" +
                            "            </h4>\n" +
                            "            <h4 class=\"\">\n" +
                            "                Country : " + event.getCountry() + " City : " + event.getCity() + " Road No. : " + event.getRoadNo() + " Building No. : " + event.getBuildingNo() + "\n" +
                            "            </h4>\n" +
                            "\n" +
                            "            <h4 class=\"\">\n" +
                            "                Time : " + event.getEventDate() + "/" + event.getEventMonth() + "/" + event.getEventYear() + "&nbsp;&nbsp;" + event.getEventHour() + ":" + event.getEventMinute() + "\n" +
                            "            </h4><h4>Join Status :" + event.getJoinStatus() + "</h4><br>");
                            if(event.getCreatorId().compareTo(currUser.getUserId())== 0){
                                out.println("<h3>Looks like you are the creator of this event.</h3>");
                            }
                            else{
                                out.println("<h4>Invited By:"+ db.getName(event.getCreatorId())+"</h4>");
                            }
                            out.println("</div><br>");
                    out.println("<div class=\"text-center\"><h1>Friends Attending</h1></div>");
                    ArrayList<Person> friendsGoing = event.getComing();
                        for (Person friend : friendsGoing) {
                            out.println("<div class=\" well row\">\n" +
                                    "    <div class=\"col-md-2\">\n" +
                                    "        <img src=\"ImageServletProcess.do?id="+friend.getProfilePicId()+"\"\n" +
                                    "             class=\"img-circle img-responsive\" width=\"60%\" />\n" +
                                    "    </div>\n" +
                                    "    <div class=\"col-md-4\">\n" +
                                    "        <div ><strong class=\"text-primary\">" + friend.getUserName() + "</strong>\n" +
                                    "        <br><strong>" + friend.getFirstName() + "</strong>\n" +
                                    "        &nbsp;<strong>" + friend.getLastName() + "</strong>\n" +

                                    "        </div>\n" +
                                    "    </div>\n" +
                                    "</div><br>");
                        }

                }
            }
            else{
                out.println("<div class=\"text-center\"><h1 class=\"text-primary\">Currently No Events In ur City</h1></div>");
            }
            session.removeAttribute("eventsInMyCity");
        }
        else if(goingEvents != null){
            out.println("<div class=\"text-center\"><h1 class=\"text-primary\">My Events</h1></div>");
            ArrayList<Event>myEvents = (ArrayList<Event>) session.getAttribute("myEvents");
            if(myEvents == null || myEvents.isEmpty()){
                out.println("<div class=\"text-center\"><h1 class=\"text-primary\">Currently No Events</h1></div>");

            }
            else{
                DatabaseConnection db = new DatabaseConnection();
                //out.println("<div class=\"container-fluid\"");
                for(Event event: myEvents){
                    //out.println("<div class=\"row well \"");
                    out.println("<div class = \"row well text-center\">\n" +
                            "            <h2 class=\"text-primary\">\n" +
                            "                Event Name : " + event.getEventName() + "\n" +
                            "            </h2>\n" +
                            "            <h3 class=\"\">\n" +
                            "                Event Type : " + event.getEventType() + "\n" +
                            "            </h3>\n" +
                            "            <h4 class=\"\">\n" +
                            "                Description : " + event.getDescription() + "\n" +
                            "            </h4>\n" +
                            "            <h4 class=\"\">\n" +
                            "                Country : " + event.getCountry() + " City : " + event.getCity() + " Road No. : " + event.getRoadNo() + " Building No. : " + event.getBuildingNo() + "\n" +
                            "            </h4>\n" +
                            "\n" +
                            "            <h4 class=\"\">\n" +
                            "                Time : " + event.getEventDate() + "/" + event.getEventMonth() + "/" + event.getEventYear() + "&nbsp;&nbsp;" + event.getEventHour() + ":" + event.getEventMinute() + "\n" +
                            "            </h4><h4>Join Status : GOING &nbsp;</h4><br>");
                    String name = db.getName(event.getCreatorId());
                    if(name.compareTo(currUser.getUserName()) == 0){
                        out.println("<h4>Created By: Me</h4>\n" +
                        "\n</div>");
                    }
                    else{
                        out.println("<h4>Invited By: "+name+"</h4>\n" +
                                "\n</div>");
                    }
                    //out.println("</div>");
                }
                //out.println("</div>");
            }
            session.removeAttribute("myEvents");
            session.removeAttribute("goingEvents");
        }
        else if(inviteGuests != null ){
            out.println("<div class=\"row\">\n" +
                    "    <div class=\"well text-center\">\n" +
                    "        <h2 class=\"text-primary\">Invite Friends</h2>\n" +
                    "    </div>\n" +
                    "</div>");
            DatabaseConnection db=new DatabaseConnection();
            String eventId = (String) session.getAttribute("eventId");
            ArrayList<Person> friends = db.getFriendLIst(currUser.getUserId());

            out.println("<div class=\"container\">");
            for(Person friend : friends){
                if(db.notInvited(eventId, friend.getUserId())){
                    out.println("<div class=\"col-md-2\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+friend.getProfilePicId()+"\"\n" +
                            "             class=\"img-circle\" width=\"70%\" />\n" +
                            "    </div>\n" +
                            "    <div class=\"col-md-2 pull-left\">\n" +
                            "        <h3 class=\"text-primary\"><b>"+friend.getUserName()+"</b></h3>\n" +
                            "        <h5>"+friend.getFirstName()+"&nbsp;"+friend.getLastName()+"</h5>\n" +
                            "        <form class=\"form-horizontal\" action=\"InviteFriendToEventProcess.do\" method=\"post\" >\n" +
                            "                <div class=\"col-md-2\">\n" +
                            "                    <input type=\"hidden\" name=\"eventId\" value="+eventId+">\n" +
                            "                    <input type=\"hidden\" name=\"userId\" value="+friend.getUserId()+">\n" +
                            "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Invite\">\n" +
                            "                </div>\n" +
                            "        </form>\n" +
                            "    </div>");
                }

            }
            out.println("</div>");
            db.close();
            session.removeAttribute("eventId");
            session.removeAttribute("inviteGuests");
        }
        else if(createEvent == null){
            out.println("<div class=\"row\">\n" +
                    "    <div class=\"well text-center\">\n" +
                    "        <h2><a href=\"PreludeToCreateEventProcess.do\" class=\"text-primary\">Create Your Event</a></h2>\n" +
                    "    </div>\n" +
                    "</div>");
            out.println("<div class=\"row\">\n" +
                    "    <div class=\"well text-center\">\n" +
                    "        <h2><a href=\"MyEventsProcess.do\" class=\"text-primary\">Events I am Attending</a></h2>\n" +
                    "    </div>\n" +
                    "</div>");
            out.println("<div class=\"row\">\n" +
                    "    <div class=\"well text-center\">\n" +
                    "        <h2><a href=\"EventsInMyCityProcess.do\" class=\"text-primary\">Events in my city friends attending</a></h2>\n" +
                    "    </div>\n" +
                    "</div>");
            ArrayList<Event> eventsInvited = (ArrayList<Event>)session.getAttribute("eventsInvited");
            ArrayList<Event> eventsCreated = (ArrayList<Event>)session.getAttribute("eventsCreated");
            out.println("<div class=\"row-fluid\">\n" +
                    "    <div class=\"col-md-6 pull-left\">\n" +
                    "        <h1 class=\"text-primary text-center\">Events You're Invited to</h1>");


            if(eventsInvited == null){
                out.println("<h3>No one invited you!!</h3>");
            }
            else{
                for(Event event : eventsInvited) {
                    if (event.getJoinStatus().compareTo("GOING") != 0) {

                        DatabaseConnection db = new DatabaseConnection();
                        out.println("<div class = \"well row text-center\">\n" +
                                "            <h2 class=\"text-primary\">\n" +
                                "                Event Name : " + event.getEventName() + "\n" +
                                "            </h2>\n" +
                                "            <h3 class=\"\">\n" +
                                "                Event Type : " + event.getEventType() + "\n" +
                                "            </h3>\n" +
                                "            <h4 class=\"\">\n" +
                                "                Description : " + event.getDescription() + "\n" +
                                "            </h4>\n" +
                                "            <h4 class=\"\">\n" +
                                "                Country : " + event.getCountry() + " City : " + event.getCity() + " Road No. : " + event.getRoadNo() + " Building No. : " + event.getBuildingNo() + "\n" +
                                "            </h4>\n" +
                                "\n" +
                                "            <h4 class=\"\">\n" +
                                "                Time : " + event.getEventDate() + "/" + event.getEventMonth() + "/" + event.getEventYear() + "&nbsp;&nbsp;" + event.getEventHour() + ":" + event.getEventMinute() + "\n" +
                                "            </h4><h4>Join Status :" + event.getJoinStatus() + "</h4><br><h4>Invited By: "+db.getName(event.getCreatorId())+"</h4>\n" +
                                "\n" +
                                " <form class=\"form-horizontal\" action=\"JoinEventProcess.do\" method=\"post\">\n" +
                                "    <div class=\"form-group\">\n" +
                                "        <label class=\"col-md-4 control-label\">Select Join Status</label>\n" +
                                "        <div class=\"col-md-4 inputGroupContainer\">\n" +
                                "            <div class=\"input-group\">\n" +
                                "                <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-list\"></i></span>\n" +
                                "                <select name=\"joinStatus\" class=\"form-control selectpicker\" >\n" +
                                "                    <option>GOING</option>\n" +
                                "                    <option>NOT GOING</option>\n" +
                                "                </select>\n" +
                                "            </div>\n" +
                                "        </div>\n" +
                                "    </div>\n" +
                                "    <div class=\"form-group\">\n" +
                                "        <label class=\"col-md-4 control-label\"></label>\n" +
                                "        <div class=\"col-md-4\">\n" +
                                "\n" +
                                "            <input type=\"hidden\"  name =\"eventId\" value=" + event.getEventId() + ">\n" +
                                "            <input type=\"submit\" class=\"btn btn-warning\" value=\"Send\">\n" +
                                "        </div>\n" +
                                "    </div>\n" +
                                "</form>       </div><br>");
                        out.println("<div class=\"text-center\"><h1>Friends Attending</h1></div>");
                        ArrayList<Person> friendsGoing = event.getComing();
                        if(friendsGoing != null && !friendsGoing.isEmpty()){
                            for(Person friend : friendsGoing){
                                out.println("<div class=\" well row\">\n" +
                                        "    <div class=\"col-md-2\">\n" +
                                        "        <img src=\"ImageServletProcess.do?id="+friend.getProfilePicId()+"\"\n" +
                                        "             class=\"img-circle img-responsive\" width=\"60%\" />\n" +
                                        "    </div>\n" +
                                        "    <div class=\"col-md-4\">\n" +
                                        "        <div ><strong class=\"text-primary\">"+friend.getUserName()+"</strong>\n" +
                                        "        <br><strong>"+friend.getFirstName()+"</strong>\n" +
                                        "        &nbsp;<strong>"+friend.getLastName()+"</strong>\n" +

                                        "        </div>\n" +
                                        "    </div>\n" +
                                        "</div><br>");
                            }
                        }
                        else{
                            out.println("<h3>No friends Going</h3>");
                        }

                    out.println("<br>");
                    }


                }
            }

            out.println("</div>");
            out.println("    <div class=\"col-md-6 pull-left\">\n" +
                    "        <h1 class=\"text-primary text-center\">Events You've Created</h1>");
            if(eventsCreated == null){
                out.println("<h3>No event created</h3>");
            }
            else{
                for(Event event : eventsCreated){
                    out.println("<div class = \"well row text-center\">\n" +
                            "            <h2 class=\"text-primary\">\n" +
                            "                Event Name : "+event.getEventName()+"\n" +
                            "            </h2>\n" +
                            "            <h3 class=\"\">\n" +
                            "                Event Type : "+event.getEventType()+"\n" +
                            "            </h3>\n" +
                            "            <h4 class=\"\">\n" +
                            "                Description : "+event.getDescription()+"\n" +
                            "            </h4>\n" +
                            "            <h4 class=\"\">\n" +
                            "                Country : "+event.getCountry()+" City : "+event.getCity()+" Road No. : "+event.getRoadNo()+" Building No. : "+event.getBuildingNo()+"\n" +
                            "            </h4>\n" +
                            "\n" +
                            "            <h4 class=\"\">\n" +
                            "                Time : "+event.getEventDate()+"/"+event.getEventMonth()+"/"+event.getEventYear()+"&nbsp;&nbsp;"+event.getEventHour()+":"+event.getEventMinute()+"\n" +
                            "            </h4>\n" +
                            "\n" +
                            "        <form class=\"pull-right\" action=\"InviteMoreFriendsProcess.do\" method=\"post\" >\n" +
                            "                <div class=\"col-md-2\">\n" +
                            "                    <input type=\"hidden\" name=\"eventId\" value="+event.getEventId()+">\n" +
                            "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Invite Friends\">\n" +
                            "                </div>\n" +
                            "        </form>\n" +
                            "        </div><br>");
                    ArrayList<Person> invitedGuests = event.getInvitedGuests();
                    if(invitedGuests != null && !invitedGuests.isEmpty()){
                        for(Person guest : invitedGuests){
                            out.println("<div class=\" well row\">\n" +
                                    "    <div class=\"col-md-2\">\n" +
                                    "        <img src=\"ImageServletProcess.do?id="+guest.getProfilePicId()+"\"\n" +
                                    "             class=\"img-circle img-responsive\" width=\"60%\" />\n" +
                                    "    </div>\n" +
                                    "    <div class=\"col-md-4\">\n" +
                                    "        <div><strong>"+guest.getUserName()+"</strong>\n" +
                                    "            <div class=\"pull-right\"><b>"+guest.getEventJoinStatus()+"</b></div>\n" +
                                    "        </div>\n" +
                                    "    </div>\n" +
                                    "</div><br>");
                        }
                    }
                    else {
                        out.println("<h3>You Invited No one.</h3>");
                    }
                    out.println("<br>");
                }
            }

            out.println("</div>");




            out.println("</div>");

            session.removeAttribute("eventsInvited");
            session.removeAttribute("eventsCreated");


        }
        else{
            out.println("<div class=\"container\">\n" +
                    "    <div class=\"text-center\">\n" +
                    "        <h2 class=\"text-primary\">Create Your Event</h2>\n" +
                    "    </div>\n" +
                    "    <form class=\"well form-horizontal\" action=\"CreateEventProcess.do\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Event Name</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-king\"></i></span>\n" +
                    "                        <input  name=\"eventName\" placeholder=\"Event Name\" class=\"form-control\"  type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Event Type</label>\n" +
                    "                <div class=\"col-md-4 selectContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-list\"></i></span>\n" +
                    "                        <select name=\"eventType\" class=\"form-control selectpicker\" >\n" +
                    "                            <option >Personal</option>\n" +
                    "                            <option>Professional</option>\n" +
                    "                            <option>Conference</option>\n" +
                    "                            <option>Grand Event</option>\n" +
                    "\n" +
                    "                        </select>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Event Description</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-pencil\"></i></span>\n" +
                    "                        <textarea class=\"form-control\" name=\"description\" placeholder=\"Event Description\"></textarea>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\" >Event Time</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-calendar\"></i></span>\n" +
                    "                        <input name=\"eventDate\" placeholder=\"DD\" class=\"form-control\"  type=\"text\">\n" +
                    "                        <input name=\"eventMonth\" placeholder=\"MM\" class=\"form-control\"  type=\"text\">\n" +
                    "                        <input name=\"eventYear\" placeholder=\"YYYY\" class=\"form-control\"  type=\"text\"><br>\n" +
                    "                        <input name=\"eventHour\" placeholder=\"HH\" class=\"form-control\"  type=\"text\">\n" +
                    "                        <input name=\"eventMinute\" placeholder=\"MI\" class=\"form-control\"  type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <!-- Text input-->\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Country</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-home\"></i></span>\n" +
                    "                        <input name=\"country\" placeholder=\"country\" class=\"form-control\"  type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "\n" +
                    "            <!-- Text input-->\n" +
                    "\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">City</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-home\"></i></span>\n" +
                    "                        <input name=\"city\" placeholder=\"city\" class=\"form-control\" type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <!-- Text input-->\n" +
                    "\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Road No.</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-road\"></i></span>\n" +
                    "                        <input name=\"roadNo\" placeholder=\"e.g. 12/A\" class=\"form-control\" type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <!-- Text input-->\n" +
                    "\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Building No.</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-home\"></i></span>\n" +
                    "                        <input name=\"buildingNo\" placeholder=\"e.g. 936/A\" class=\"form-control\"  type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-send\"></i></span>\n" +
                    "                        <input  class=\"btn btn-warning\"  type=\"Submit\" value=\"Create Event\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "\n" +
                    "    </form>\n" +
                    "</div>");
            session.removeAttribute("createEvent");

        }
    }

%>

<%--
<form class="form-horizontal" action="joinEventProcess.do" method="post">
    <div class="form-group">
        <label class="col-md-4 control-label">Select Join Status</label>
        <div class="col-md-4 inputGroupContainer">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
                <select name="visibility" class="form-control selectpicker" >
                    <option>GOING</option>
                    <option>NOT GOING</option>
                </select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-4 control-label"></label>
        <div class="col-md-4">

            <input type="hidden"  name ="eventId" value="event.getEventId()">
            <input type="submit" class="btn btn-warning" value="Send">
        </div>
    </div>
</form>

<div class="modal-body row-fluid">
    <div class="span6 well">
        <h1 class="text-primary text-center">Events You're Invited to</h1>
        <div class = "well">
            <h2 class="text-primary">
                Event Name :
            </h2>
            <h3 class="">
                Event Type :
            </h3>
            <h4 class="">
                Description
            </h4>
            <h4 class="">
                Country :  City :  Road No. :  Building No. :
            </h4>

            <h4 class="">
                Time :
            </h4>

        </div>
    </div>
    <div class="span6">

    </div>
</div>
<div class="row">
    <div class="well text-center">
        <h2><a href="PreludeToCreateEventProcess.do" class="text-primary">Create Your Event</a></h2>
    </div>
</div>



<div class="container">
    <div class="text-center">
        <h2 class="text-primary">Create Your Event</h2>
    </div>
    <form class="well form-horizontal" action="CreateEventProcess.do" method="post">
            <div class="form-group">
                <label class="col-md-4 control-label">Event Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-king"></i></span>
                        <input  name="eventName" placeholder="Event Name" class="form-control"  type="text">
                    </div>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-4 control-label">Event Type</label>
                <div class="col-md-4 selectContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
                        <select name="eventType" class="form-control selectpicker" >
                            <option >Personal</option>
                            <option>Professional</option>
                            <option>Conference</option>
                            <option>Grand Event</option>

                        </select>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label">Event Description</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                        <textarea class="form-control" name="description" placeholder="Event Description"></textarea>
                    </div>
                </div>
            </div>


            <div class="form-group">
                <label class="col-md-4 control-label" >Event Time</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                        <input name="eventDate" placeholder="DD" class="form-control"  type="text">
                        <input name="eventMonth" placeholder="MM" class="form-control"  type="text">
                        <input name="eventYear" placeholder="YYYY" class="form-control"  type="text"><br>
                        <input name="eventHour" placeholder="HH" class="form-control"  type="text">
                        <input name="eventMinute" placeholder="MI" class="form-control"  type="text">
                    </div>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">Country</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
                        <input name="country" placeholder="country" class="form-control"  type="text">
                    </div>
                </div>
            </div>


            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">City</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
                        <input name="city" placeholder="city" class="form-control" type="text">
                    </div>
                </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Road No.</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
                        <input name="roadNo" placeholder="e.g. 12/A" class="form-control" type="text">
                    </div>
                </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
                <label class="col-md-4 control-label">Building No.</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
                        <input name="buildingNo" placeholder="e.g. 936/A" class="form-control"  type="text">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-send"></i></span>
                        <input  class="form-control btn btn-warning"  type="Submit" value="Create Event">
                    </div>
                </div>
            </div>


    </form>
</div>




<div class="container">

    <div class="col-md-2">
        <img src="media/bleach.jpg"
             class="img-circle" width="70%" />
    </div>
    <div class="well col-md-4 pull-left">
        <h2 class="text-primary"><b>Saiful</b></h2>
        <h3>Saiful Islam</h3>
        <form class="form-horizontal" action="InviteFriendToEventProcess.do" method="post"  id="contact_form">
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="hidden" name="eventId" value="eventId">
                    <input type="hidden" name="userId" value="friend.getUserId()">
                    <input type="submit" class="btn btn-warning" value="Invite">
                </div>
            </div>
        </form>
    </div>

</div>--%>


</body>



<jsp:include page="foot.jsp" />

