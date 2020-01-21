<%@ page import="Models.Person" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/12/2016
  Time: 11:09 AM
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
%>

</body>
<jsp:include page="foot.jsp" />
