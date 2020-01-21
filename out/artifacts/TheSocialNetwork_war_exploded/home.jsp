<%@ page import="Utilities.DatabaseConnection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.Posts" %>
<%@ page import="Models.Person" %>
<%@ page import="Models.Comments" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/6/2016
  Time: 2:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="base.jsp" />
<body class = "container-fluid">
<jsp:include page="nav.jsp" />
<%
    Person currUser = (Person) session.getAttribute("currUser");
    //String id = (String) session.getAttribute("userID");
    if(currUser == null){
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
    else{
        System.out.println("before creating database connection");
        DatabaseConnection db = new DatabaseConnection();
        //ArrayList<Posts>posts = new ArrayList<Posts>();
        System.out.println("before Function call");
        ArrayList<Posts> posts = db.getPosts(currUser.getUserId());
        System.out.println("After the function call");
        if(!posts.isEmpty()){
            System.out.println("\n\n\nInside the \n\n\n if");
            session.setAttribute("posts", posts);
        }
    }

%>
    <div class="container">
        <form class="well form-horizontal" action="PostProcess.do" method="post" >
            <div class="form-group">
                <label class="col-md-4 control-label">Write a post</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                        <textarea class="form-control" name="post" placeholder="write something you wanna tell."></textarea>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label">Select Visibility</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
                        <select name="visibility" class="form-control selectpicker" >
                            <option>Everyone</option>
                            <option>Friends</option>
                            <option>Only Me</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="submit" class="btn btn-warning" value="Post It">
                </div>
            </div>
        </form>

    </div>

<%
    if(session.getAttribute("posts") != null){

        ArrayList<Posts>wallPosts = (ArrayList<Posts>) session.getAttribute("posts");
        //ArrayList<Comments>comments = (ArrayList<Comments>) session.getAttribute("posts");
        System.out.println("Got \n\n\nwallposts");
        if(wallPosts == null){
            out.println("");
        }
        else if(wallPosts.isEmpty()){
            out.println("You have no posts.");
        }
        else{

            out.println("<div class=\"container\">");
            for(Posts post : wallPosts){
                out.println("<div class=\"row\">\n" +
                        "    <div class=\"col-md-2\">\n" +
                        "        <img src=\"ImageServletProcess.do?id="+post.getPosterPicId()+"\" alt=\"The pro pic\"\n" +
                        "             class=\"img-circle\" width=\"80%\" />\n" +
                        "    </div>" +
                        "<form class=\" pull-right\" method=\"post\" action = \"PostLikeProcess.do\">" +
                        "<input type=\"hidden\" name = \"postId\" value="+post.getPostId()+">"+
                        "<button type=\"submit\" class=\"btn btn-warning glyphicon glyphicon-thumbs-up pull-right\"></button></form>\n" +
                        "<h4 class=\"pull-right\">"+post.getLikes()+"</h4>&nbsp;"+
                        "    <div class=\"col-md-10\">\n"+
                        "        <div><b>"+post.getPosterName()+"</b> : ");
                DatabaseConnection db = new DatabaseConnection();
                String groupName = db.getGroupNameByPost(post.getPostId());
                if(groupName!=null){
                    out.println("<strong> "+groupName+"</strong>");
                }
                out.println("        </div>\n" +
                        "        <div>"+post.getContents()+"</div><br><br>\n" );
                ArrayList<Comments> coms = post.getComments();
                if(coms == null || coms.isEmpty()){
                    out.println("");
                }
                else{
                    //out.println("<div class=\"row\">");
                    for(Comments comment: coms){
                        out.println("<div class=\"row\">\n" +
                                "<div class=\"col-md-2 pull-left\" >\n" +
                                "    <img src=\"ImageServletProcess.do?id="+comment.getCommenterPicId()+"\" alt=\"The pro pic\"\n" +
                                "         class=\"img-circle img-responsive\" width=\"70%\" />\n" +
                                "</div>\n" +
                                "<div class=\"col-md-6\">\n" +
                                "    <div><b>"+comment.getCommenterName()+"</b>\n" +
                                "    </div>\n" +
                                "    <div>"+comment.getContents()+"</div>\n" +
                                "<form class=\" pull-left\" method=\"post\" action = \"CommentLikeProcess.do\">" +
                                "<input type=\"hidden\" name = \"commentId\" value="+comment.getCommentId()+">"+
                                "<button type=\"submit\" class=\"btn btn-warning glyphicon glyphicon-thumbs-up pull-left\"></button></form>\n" +
                                "&nbsp;<h4 class=\"pull-left\">"+comment.getLikes()+"</h4>"+
                                "</div></div><br><br>\n");
                    }
                    //out.println("</div>");
                }
                out.println("<form class=\"well form-horizontal\" action=\"CommentProcess.do\" method=\"post\" >\n" +
                        "            <div class=\"form-group\">\n" +
                        "\n" +
                        "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                        "                    <div class=\"input-group\">\n" +
                        "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-pencil\"></i></span>\n" +
                        "                        <textarea class=\"form-control\" name=\"comment\" placeholder=\"write a comment.\"></textarea>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <div class=\"form-group pull-left\">\n" +
                        "\n" +
                        "                <div class=\"col-md-4\">\n" +
                        "                    <input type=\"hidden\" name=\"postId\" value="+post.getPostId()+">\n" +
                        "                    <input type=\"hidden\" name=\"commenterId\" value="+currUser.getUserId()+">\n" +
                        "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"comment It\">\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </form>");

                out.println("\n" +
                        "    </div>\n" +
                        "</div><br><br>");
            }
            out.println("</div>");
        }


    }


%>
<%--
<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-2">
        <img src="media/bleach.jpg" alt="The pro pic"
                class="img-circle" width="100%" />
    </div>
    <div class="col-md-8">
        <div><b>post.getPosterName()</b>
        </div>
        <div>post.getContents()</div>

    </div>
    </div><br>

<div class="col-md-2">
    <img src="media/bleach.jpg" alt="The pro pic"
         class="img-circle" width="80%" />
</div>
<div class="col-md-6">
    <div><b>comment.getCommenterName()</b>
    </div>
    <div>comment.getContents()</div>

</div>
--%>
<%--
<div class="container">
    <div class="col-md-8 pull-right">
        <form class="well form-horizontal" action="PostProcess.do" method="post" >
            <div class="form-group">

                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                        <textarea class="form-control" name="comment" placeholder="write a comment."></textarea>
                    </div>
                </div>
            </div>
            <div class="form-group pull-left">

                <div class="col-md-4">
                    <input type="submit" class="btn btn-warning" value="comment It">
                </div>
            </div>
        </form>
    </div>
</div>


--%>






</body>



<jsp:include page="foot.jsp" />