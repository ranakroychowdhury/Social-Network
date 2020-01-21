<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/5/2016
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="base.jsp" />



  <%--<h3>msg</h3>--%>
  <%
    String msg = (String) session.getAttribute("Message");
    if(msg != null){
      out.println("<h3>"+msg+"</h3>");
      session.removeAttribute("Message");
    }


  %>
  <form method="post" action="LoginProcess.do">

    Enter your username: <input type="text" name="userName" /> <br/>
    Enter your password: <input type="password" name="password" /> <br/>
    <input type="submit" value="Login" /> <br/>
  </form>
  <h2>Don't Have a account?</h2>
  <p>Click <a href="regForm.jsp">here</a> to Sign Up.</p>


<jsp:include page="foot.jsp" />