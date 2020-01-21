<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/4/2016
  Time: 3:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="base.jsp" />



<%
    String msg = (String) session.getAttribute("Message");
    if(msg != null){
        out.println("<h3>"+msg+"</h3>");

    }


%>












<body>

    <form  method="post" action="RegProcess.do" enctype="multipart/form-data">
        <table>
            <tr>
                <td>First Name : </td>
                <td> <input name="firstName" placeholder="Enter your first name" class="form-control"  type="text"></td>
            </tr>


        <tr>
            <td>Last Name : </td>
            <td><input name="lastName" placeholder="Enter your last name" class="form-control"  type="text"></td>
        </tr>



            <tr>
                <td>Username : </td>
                <td> <input name="userName" placeholder="Enter your username"   type="text"></td>
            </tr>




            <tr>
                <td>Birthday : </td>
                <td><input name="birthDate" placeholder="DD"   type="text"></td>
                <td><input name="birthMonth" placeholder="MM"  type="text"></td>
                <td><input name="birthYear" placeholder="YYYY"  type="text"></td>
            </tr>





            <tr>
                <td>Gender : </td>
                <td><input name="gender" value="MALE"  type="radio">Male </td>
                <td> <input name="gender" value="FEMALE"  type="radio">Female</td>
                <td><input name="gender" value="OTHERS"  type="radio">Otherstd</td>

            </tr>





            <tr>
                <td>Email : </td>
                <td><input name="email" placeholder="Enter your email"  type="email"></td>
            </tr>




            <tr>
                <td>Password : </td>
                <td><input type="password" name="password"/></td>
            </tr>

            <tr>
                <td>Phone : </td>
                <td><input type="text" name="phone" /></td>
            </tr>

            <tr>
                <td>Country : </td>
                <td><input type="text" name="country" /></td>
            </tr>

            <tr>
                <td>State : </td>
                <td><input type="text" name="state" /></td>
            </tr>

            <tr>
                <td>City : </td>
                <td><input type="text" name="city" /></td>
            </tr>

            <tr>
                <td>Zip Code : </td>
                <td><input type="text" name="zipCode" /></td>
            </tr>

            <tr>
                <td>Cover Photo : </td>
                <td><input type="file" name="coverPhoto" size = "60"/></td>
            </tr>

            <tr>
                <td>Profile Pic : </td>
                <td><input type="file" name="profilePic" size="60"/></td>
            </tr>

            <tr>
                <td></td>
                <td><input type="submit" value="Sign Up" /></td>
            </tr>
        </table>
    </form>
</body>
