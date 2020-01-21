<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/4/2016
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="home.jsp">The Social Network</a>
        </div>
        <ul class="nav navbar-nav">
            <li ><a href="home.jsp">Home</a></li>
            <li><a href="message.jsp">Messages</a></li>
            <li><a href="AlbumProcess.do">Albums</a></li>
            <li><a href="EventProcess.do">Events</a></li>
            <li><a href="GroupProcess.do">Groups</a></li>
            <li><a href="friendRequests.jsp">Firend Requests</a></li>
            <li><a href="FriendListProcess.do">Firend Lists</a></li>
        </ul>
        <form class="navbar-form navbar-right" method="get" action="LogOut.do">
            <input type="submit" class="btn btn-warning" value="Log Out">

        </form>
        <form class="navbar-form navbar-right" method="post" action="SearchProcess.do">
            <div class="form-group">
                <input name="userName" type="text" class="form-control" placeholder="Search">
            </div>
            <input type="submit" class="btn btn-default" value="search">

        </form>

    </div>
</nav>
