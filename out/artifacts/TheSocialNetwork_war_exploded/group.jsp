<%@ page import="Models.Person" %>
<%@ page import="Utilities.DatabaseConnection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.Group" %>
<%@ page import="Models.Posts" %>
<%@ page import="Models.Comments" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/19/2016
  Time: 4:37 PM
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
        String createGroup = (String) session.getAttribute("createGroup");
        String addMembers = (String) session.getAttribute("addMembers");
        Group currGroup = (Group) session.getAttribute("currGroup");
        if(addMembers != null){
            String groupId = (String) session.getAttribute("groupId");
            if(groupId != null){
                out.println("<div class=\"row\">\n" +
                        "    <div class=\"well text-center\">\n" +
                        "        <h2 class=\"text-primary\">Add Friends</h2>\n" +
                        "    </div>\n" +
                        "</div>");
                DatabaseConnection db=new DatabaseConnection();
                ArrayList<Person> friends = db.getFriendLIst(currUser.getUserId());

                out.println("<div class=\"container\">");
                for(Person friend : friends) {

                    if (db.notMember(friend.getUserId(), groupId)) {
                        out.println("<div class=\"col-md-2\">\n" +
                                "        <img src=\"ImageServletProcess.do?id="+friend.getProfilePicId()+"\"\n" +
                                "             class=\"img-circle img-responsive\" width=\"70%\" />\n" +
                                "    </div>\n" +
                                "    <div class=\"well col-md-4\">\n" +
                                "        <h3 class=\"text-primary\"><b>" + friend.getUserName() + "</b></h3>\n" +
                                "        <h5>" + friend.getFirstName() + "&nbsp;" + friend.getLastName() + "</h5>\n" +
                                "        <form class=\"form-horizontal\" action=\"AddMembersGroupProcess.do\" method=\"post\" >\n" +
                                "                <div class=\"col-md-2\">\n" +
                                "                    <select name=\"memberType\" class=\"form-control selectpicker\" >\n" +
                                "                       <option>REGULAR</option>\n"+
                                "                       <option>ADMIN</option>\n"+
                                "                      </select>\n"+
                                "                </div>\n" +
                                "                <div class=\"col-md-2\">\n" +
                                "                    <input type=\"hidden\" name=\"groupId\" value=" + groupId + ">\n" +
                                "                    <input type=\"hidden\" name=\"userId\" value=" + friend.getUserId() + ">\n" +
                                "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Add\">\n" +
                                "                </div>\n" +
                                "        </form>\n" +
                                "    </div>");
                    }

                }
                db.close();
            }
            session.removeAttribute("groupId");
            session.removeAttribute("addMembers");
        }
        else if(currGroup != null){
            DatabaseConnection db = new DatabaseConnection();
            currUser.setMemberType(db.getMemberType(currGroup.getGroupId(), currUser.getUserId()));
            out.println("<div class=\"container\"");
            out.println("<div class=\"row\">");
            out.println("<form class=\"well form-horizontal\" method=\"post\" action=\"GroupMembers.do\">\n" +
                    "                    <input type=\"hidden\" name=\"groupId\" value="+ currGroup.getGroupId() +">\n" +
                    "                    <input type=\"hidden\" name=\"groupName\" value="+ currGroup.getGroupName() +">\n" +
                    "                    <input type=\"hidden\" name=\"description\" value="+ currGroup.getDescription() +">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+ currGroup.getCreatorId() +">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+ currUser.getUserId() +">\n" +
                    "            <input type=\"submit\" class=\"btn btn-warning\" value=\"Group Members\">\n" +
                    "\n" +
                    "        </form>");
            out.println("<form class=\"well form-horizontal\" method=\"post\" action=\"GroupMembersFriends.do\">\n" +
                    "                    <input type=\"hidden\" name=\"groupId\" value="+ currGroup.getGroupId() +">\n" +
                    "                    <input type=\"hidden\" name=\"groupName\" value="+ currGroup.getGroupName() +">\n" +
                    "                    <input type=\"hidden\" name=\"description\" value="+ currGroup.getDescription() +">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+ currGroup.getCreatorId() +">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+ currUser.getUserId() +">\n" +
                    "            <input type=\"submit\" class=\"btn btn-warning\" value=\"Members who friends\">\n" +
                    "\n" +
                    "        </form>");
            out.println("<form class=\"well form-horizontal\" method=\"post\" action=\"GroupPostsFriends.do\">\n" +
                    "                    <input type=\"hidden\" name=\"groupId\" value="+ currGroup.getGroupId() +">\n" +
                    "                    <input type=\"hidden\" name=\"groupName\" value="+ currGroup.getGroupName() +">\n" +
                    "                    <input type=\"hidden\" name=\"description\" value="+ currGroup.getDescription() +">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+ currGroup.getCreatorId() +">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+ currUser.getUserId() +">\n" +
                    "            <input type=\"submit\" class=\"btn btn-warning\" value=\"Posts of friends\">\n" +
                    "\n" +
                    "        </form>");
            out.println("<form class=\"well form-horizontal\" method=\"post\" action=\"PreludeToAddMembers.do\">\n" +
                    "                    <input type=\"hidden\" name=\"groupId\" value="+ currGroup.getGroupId() +">\n" +
                    "                    <input type=\"hidden\" name=\"groupName\" value="+ currGroup.getGroupName() +">\n" +
                    "                    <input type=\"hidden\" name=\"description\" value="+ currGroup.getDescription() +">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+ currGroup.getCreatorId() +">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+ currUser.getUserId() +">\n" +
                    "            <input type=\"submit\" class=\"btn btn-warning\" value=\"Add Members\">\n" +
                    "\n" +
                    "        </form>");
            out.println("</div></div>");
            out.println("<div class=\"container\">\n" +
                    "        <form class=\"well form-horizontal\" action=\"GroupPostProcess.do\" method=\"post\" >\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Write a post</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-pencil\"></i></span>\n" +
                    "                        <textarea class=\"form-control\" name=\"groupPost\" placeholder=\"write something you wanna say.\"></textarea>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Select Visibility</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-list\"></i></span>\n" +
                    "                        <select name=\"visibility\" class=\"form-control selectpicker\" >\n" +
                    "                            <option>Everyone</option>\n" +
                    "                            <option>Friends</option>\n" +
                    "                            <option>Only Me</option>\n" +
                    "                        </select>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"hidden\" name=\"groupId\" value="+ currGroup.getGroupId() +">\n" +
                    "                    <input type=\"hidden\" name=\"groupName\" value="+ currGroup.getGroupName() +">\n" +
                    "                    <input type=\"hidden\" name=\"description\" value="+ currGroup.getDescription() +">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+ currGroup.getCreatorId() +">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+ currUser.getUserId() +">\n" +
                    "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Post It\">\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "\n" +
                    "    </div>");
            out.println("<div class=\"container\"");

            ArrayList<Person> members = currGroup.getMember();
            if(members!=null && !members.isEmpty()){
                for(Person person: members){
                    out.println("<div class=\"row \">\n" +
                            "    <div class=\"col-md-2\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                            "             class=\"img-circle img-responsive\" width=\"90%\" />\n" +
                            "    </div>\n" +
                            "    <div class=\"well col-md-10\">\n" +
                            "        <div><b>"+person.getUserName()+"</b>\n" +
                            "            <div class=\"pull-right muted\">"+person.getMemberType()+"</div>\n" +
                            "        </div>\n" +
                            "        <div>Username : "+person.getUserName()+" </div>\n" +
                            "        <div>Full Name : "+person.getFirstName()+"&nbsp;"+person.getLastName()+"</div>" +
                            "        <form class=\"well form-horizontal\" action=\"RemoveMemberProcess.do\" method=\"post\" >\n" +
                            "            <div class=\"form-group\">\n" +
                            "                <label class=\"col-md-4 control-label\"></label>\n" +
                            "                <div class=\"col-md-4\">\n" +
                            "\n");

                    if(currUser.getMemberType().compareTo("ADMIN")==0){
                        out.println("                    <input type=\"hidden\" name=\"groupId\" value="+ currGroup.getGroupId() +">\n" +
                                "                    <input type=\"hidden\" name=\"groupName\" value="+ currGroup.getGroupName() +">\n" +
                                "                    <input type=\"hidden\" name=\"description\" value="+ currGroup.getDescription() +">\n" +
                                "                    <input type=\"hidden\" name=\"creatorId\" value="+ currGroup.getCreatorId() +">\n" +
                                "                    <input type=\"hidden\" name=\"memberId\" value="+ person.getUserId() +">\n" +
                                "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Remove Member\">\n"
                                );
                    }
                           out.println("                </div>\n" +
                                   "            </div>\n" +
                                   "        </form></div>");

                }
            }


            ArrayList<Posts> groupPosts = currGroup.getGroupPosts();
            if(groupPosts!=null && !groupPosts.isEmpty()){
                for(Posts post : groupPosts){
                    out.println("<div class=\"row\">\n" +
                            "    <div class=\"col-md-2\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+post.getPosterPicId()+"\" alt=\"The pro pic\"\n" +
                            "             class=\"img-circle\" width=\"80%\" />\n" +
                            "    </div>" +
                            "<form class=\" pull-right\" method=\"post\" action = \"PostLikeProcess.do\">" +
                            "<input type=\"hidden\" name = \"postId\" value="+post.getPostId()+">"+
                            "<button type=\"submit\" class=\"btn btn-warning glyphicon glyphicon-thumbs-up pull-right\"></button></form>\n" +
                            "<h4 class=\"pull-right\">"+post.getLikes()+"</h4>&nbsp;"+
                            "    <div class=\"col-md-10\">\n" +
                            "        <div><b>"+post.getPosterName()+"</b>\n" +
                            "        </div>\n" +
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
                                    "         class=\"img-circle\" width=\"70%\" />\n" +
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
                            "<br><br>");

                }
                out.println("</div>");
            }
            session.removeAttribute("currGroup");
        }

        else if(createGroup != null){
            out.println("<div class=\"container\">\n" +
                    "    <div class=\"text-center\">\n" +
                    "        <h2 class=\"text-primary\">Create Your Group</h2>\n" +
                    "    </div>\n" +
                    "    <form class=\"well form-horizontal\" action=\"CreateGroupProcess.do\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Group Name</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-king\"></i></span>\n" +
                    "                        <input  name=\"groupName\" placeholder=\"Group Name\" class=\"form-control\"  type=\"text\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Group Description</label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-pencil\"></i></span>\n" +
                    "                        <textarea class=\"form-control\" name=\"description\" placeholder=\"Group Description\"></textarea>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4 inputGroupContainer\">\n" +
                    "                    <div class=\"input-group\">\n" +
                    "                        <span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-send\"></i></span>\n" +
                    "                        <input  name=\"creatorId\"  type=\"hidden\" value="+currUser.getUserId()+">\n" +
                    "                        <input  class=\"btn btn-warning\"  type=\"Submit\" value=\"Create Group\">\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "\n" +
                    "\n" +
                    "    </form>\n" +
                    "</div>");
            session.removeAttribute("createGroup");

        }
        else{

            out.println("<div class=\"row\">\n" +
                    "    <div class=\"well text-center\">\n" +
                    "        <h2><a href=\"PreludeToCreateGroupProcess.do\" class=\"text-primary\">Create A Group</a></h2>\n" +
                    "    </div>\n" +
                    "</div>");
            ArrayList<Group> myGroups = (ArrayList<Group>) session.getAttribute("myGroups");
            DatabaseConnection db = new DatabaseConnection();
            if(myGroups!= null && !myGroups.isEmpty()){
                out.println("<div class=\"container\">");
                for(Group group: myGroups){
                    out.println("<div class=\"row well text-center\">\n" +
                            "        <h2>"+group.getGroupName()+"</h2>\n" +
                            "        <h4>"+group.getDescription()+"</h4>\n" +
                            "        <h3>"+db.getName(group.getCreatorId()) +"</h3>\n" +
                            "        <form class=\"form-horizontal\" action=\"SelectGroupProcess.do\" method=\"post\"  id=\"contact_form\">\n" +
                            "            <div class=\"form-group\">\n" +
                            "                <label class=\"col-md-4 control-label\"></label>\n" +
                            "                <div class=\"col-md-4\">\n" +
                            "                    <input type=\"hidden\" name=\"groupId\" value="+group.getGroupId()+">\n" +
                            "                    <input type=\"hidden\" name=\"groupName\" value="+group.getGroupName()+">\n" +
                            "                    <input type=\"hidden\" name=\"description\" value="+group.getDescription()+">\n" +
                            "                    <input type=\"hidden\" name=\"creatorId\" value="+group.getCreatorId()+">\n" +
                            "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Enter Group\">\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </form>\n" +
                            "    </div><br><br>");
                }

                out.println("</div>");
                db.close();
                session.removeAttribute("myGroups");
            }
            else{
                out.println("<h1>You have no groups.</h1>");
            }
        }

    }
%>
<%--
<div class="container">
    <div class="row well text-center">
        <h2>GroupName</h2>
        <h4>Description</h4>
        <h3>db.getName(creatorId)</h3>
        <form class="form-horizontal" action="SelectGroupProcess.do" method="post"  id="contact_form">
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="submit" class="btn btn-warning" value="Enter Group">
                </div>
            </div>
        </form>
    </div>
</div>
--%>
</body>

<jsp:include page="foot.jsp" />
