<%@ page import="Models.Person" %>
<%@ page import="Utilities.DatabaseConnection" %>
<%@ page import="Models.Album" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.Picture" %><%--
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
    else{

        Album currAlbum = (Album) session.getAttribute("currAlbum");
        String albumId = (String)session.getAttribute("albumId");

        ArrayList<Album> myAlbums = (ArrayList<Album>)session.getAttribute("myAlbums");
        if(albumId!=null){
            out.println("<div class=\"row\">\n" +
                    "    <div class=\"well text-center\">\n" +
                    "        <h2 class=\"text-primary\">Add Friends</h2>\n" +
                    "    </div>\n" +
                    "</div>");
            DatabaseConnection db=new DatabaseConnection();
            ArrayList<Person> friends = db.getFriendLIst(currUser.getUserId());

            out.println("<div class=\"container\">");
            for(Person friend : friends) {

                if (db.notCollaborator(friend.getUserId(), albumId)) {
                    out.println("<div class=\"col-md-2\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+friend.getProfilePicId()+"\"\n" +
                            "             class=\"img-circle img-responsive\" width=\"70%\" />\n" +
                            "    </div>\n" +
                            "    <div class=\"well col-md-4\">\n" +
                            "        <h3 class=\"text-primary\"><b>" + friend.getUserName() + "</b></h3>\n" +
                            "        <h5>" + friend.getFirstName() + "&nbsp;" + friend.getLastName() + "</h5>\n" +
                            "        <form class=\"form-horizontal\" action=\"AddCollaboratorProcess.do\" method=\"post\" >\n" +
                            "                <div class=\"col-md-2\">\n" +
                            "                    <input type=\"hidden\" name=\"albumId\" value=" + albumId + ">\n" +
                            "                    <input type=\"hidden\" name=\"userId\" value=" + friend.getUserId() + ">\n" +
                            "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"Add\">\n" +
                            "                </div>\n" +
                            "        </form>\n" +
                            "    </div>");
                }

            }
            out.println("</div>");
            db.close();
            session.removeAttribute("albumId");
        }
        else if(currAlbum != null){
            ArrayList<Picture> pictures = currAlbum.getPics();
            ArrayList<Person> collaborators = currAlbum.getCollaborater();

            out.println("<div class=\"row well text-center\">\n" +
                    "        <form class=\"form-horizontal\" action=\"UploadPictureProcess.do\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Picture Name: </label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"text\" name=\"picName\">\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Caption: </label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"text\" name=\"caption\">\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"hidden\" name=\"albumId\" value="+currAlbum.getAlbumId()+">\n" +
                    "                    <input type=\"hidden\" name=\"albumName\" value="+currAlbum.getAlbumName()+">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+currAlbum.getCreatorId()+">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                    "                    <input type=\"file\" class=\"btn btn-warning\" name=\"picture\" size=\"100\">\n" +
                    "<input type=\"submit\" class=\"btn btn-warning\" value=\"Upload Pic\">\n"+
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "    </div>");



            out.println("<div class=\"row well text-center\">\n" +
                    "        <form class=\"form-horizontal\" action=\"PreludeAddCollabProcess.do\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"hidden\" name=\"albumId\" value="+currAlbum.getAlbumId()+">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                    "<input type=\"submit\" class=\"btn btn-warning\" value=\"Add Collaborator\">\n"+
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "    </div>");
            out.println("<div class=\"row well text-center\">\n" +
                    "        <form class=\"form-horizontal\" action=\"SeeCollaboratorsProcess.do\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"hidden\" name=\"albumId\" value="+currAlbum.getAlbumId()+">\n" +
                    "                    <input type=\"hidden\" name=\"albumName\" value="+currAlbum.getAlbumName()+">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+currAlbum.getCreatorId()+">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                    "<input type=\"submit\" class=\"btn btn-warning\" value=\"See Collaborators\">\n"+
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "    </div>");

            out.println("<div class=\"row well text-center\">\n" +
                    "        <form class=\"form-horizontal\" action=\"AlbumsFriendColProcess.do\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"hidden\" name=\"albumId\" value="+currAlbum.getAlbumId()+">\n" +
                    "                    <input type=\"hidden\" name=\"albumName\" value="+currAlbum.getAlbumName()+">\n" +
                    "                    <input type=\"hidden\" name=\"creatorId\" value="+currAlbum.getCreatorId()+">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                    "<input type=\"submit\" class=\"btn btn-warning\" value=\"Whose Album I am Collaborating\">\n"+
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "    </div>");

            if(pictures!=null && !pictures.isEmpty()){
                DatabaseConnection db = new DatabaseConnection();
                out.println("<div class=\"container\">");
                for (Picture pic : pictures){
                    out.println("    <div class=\"col-md-3 pull-left center-block\">\n" +
                            "        <img src=\"ImageServletProcess.do?id="+pic.getPictureId()+"\"\n" +
                            "             class=\"img-circle\" width=\"100%\" />\n" +
                            "<h2>Name: "+pic.getPictureName()+"</h2>\n" +
                            " <h3>Caption: "+pic.getCaption()+"</h3>\n" +
                            "        <h3>Uploader: "+db.getName(pic.getUserId())+"</h3>\n"+
                            "    </div>\n"
                            );
                }
                out.println("</div>");
                db.close();
            }
            if(collaborators!=null ){
                if(!collaborators.isEmpty()){
                    out.println("<div class=\"container\"");
                    for (Person person : collaborators) {
                        out.println("<div class=\"row\">\n" +
                                "    <div class=\"col-md-2\">\n" +
                                "        <img src=\"ImageServletProcess.do?id="+person.getProfilePicId()+"\"\n" +
                                "             class=\"img-circle\" width=\"100%\" />\n" +
                                "    </div>\n" +
                                "    <div class=\"col-md-8\">\n" +
                                "        <div><h1>" + person.getUserName() + "</h1>\n" +
                                "        </div>\n" +
                                "        <h3>First Name :" + person.getFirstName() + " </h3>\n" +
                                "        <h3>Last Name : " + person.getLastName() +  "</h3>\n" +
                                "    </div>\n" +
                                "</div><br><br>\n");
                    }
                    out.println("</div>");
                }
            }


            session.removeAttribute("currAlbum");
        }
        else if(myAlbums!=null){
            out.println("<div class=\"container\">");
            out.println("<div class=\"row well text-center\">\n" +
                    "        <form class=\"form-horizontal\" action=\"CreateAlbumProcess.do\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\">Album Name</label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"text\" name=\"albumName\">\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label class=\"col-md-4 control-label\"></label>\n" +
                    "                <div class=\"col-md-4\">\n" +
                    "                    <input type=\"hidden\" name=\"userId\" value="+currUser.getUserId()+">\n" +
                    "<input type=\"submit\" class=\"btn btn-warning\" value=\"Create Album\">\n"+
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "    </div>");

            if(!myAlbums.isEmpty()){
                DatabaseConnection db = new DatabaseConnection();
                out.println("<div class=\"container\">");
                for(Album album : myAlbums){
                    out.println("<div class=\"row well text-center\">\n" +
                            "        <h2>"+album.getAlbumName()+"</h2>\n" +
                            "        <h3>"+db.getName(album.getCreatorId())+"</h3>\n" +
                            "        <form class=\"form-horizontal\" action=\"SelectAlbumProcess.do\" method=\"post\">\n" +
                            "            <div class=\"form-group\">\n" +
                            "                <label class=\"col-md-4 control-label\"></label>\n" +
                            "                <div class=\"col-md-4\">\n" +
                            "                    <input type=\"hidden\" name=\"albumId\" value="+album.getAlbumId()+">\n" +
                            "                    <input type=\"hidden\" name=\"albumName\" value="+album.getAlbumName()+">\n" +
                            "                    <input type=\"hidden\" name=\"creatorId\" value="+album.getCreatorId()+">\n" +
                            "                    <input type=\"submit\" class=\"btn btn-warning\" value=\"See Album\">\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </form>\n" +
                            "    </div>");
                }
                out.println("</div>");
                db.close();
            }
            out.println("</div>");
            session.removeAttribute("myAlbums");
        }

    }

%>
<%--
<div class="container">
    <div class="row well text-center">
        <form class="form-horizontal" action="UploadPictureProcess.do" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <input type="hidden" name="albumId" value="currAlbum.getAlbumId()">
                    <input type="hidden" name="userId" value="currUser.getUserId()">
                    <input type="file" class="btn btn-warning" name="picture" size="100">
                    <input type="submit" class="btn btn-warning" value="Upload Pic">
                </div>
            </div>
        </form>
    </div>
</div>
--%>


</body>

<jsp:include page="foot.jsp" />
