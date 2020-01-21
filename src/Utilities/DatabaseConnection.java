package Utilities;

/**
 * Created by ASUS on 12/4/2016.
 */

import Models.*;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    String dbURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    String username = "hr";
    String password = "hr";

    Connection conn = null;

    public DatabaseConnection() {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(dbURL, username, password);
            if(conn!=null) {}//System.out.println("Connection successfully established.");
            else System.out.println("Could not establish connection");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public long createAccount(String birthDate, String birthMonth, String birthYear, InputStream inputStreamProfile, String userName, String password, String firstName, String lastName, String email, String phone, String gender, String country, String state, String city, String zipCode, InputStream inputStreamCover
                             )
    {
        try
        {
            String insertCommand = "{CALL PROC_INSERT_USER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);

            stmt.setString(1, birthDate);
            stmt.setString(2, birthMonth);
            stmt.setString(3, birthYear);
            stmt.setBlob(4, inputStreamProfile);
            stmt.setString(5, userName);
            stmt.setString(6, password);
            stmt.setString(7, firstName);
            stmt.setString(8, lastName);
            stmt.setString(9, email);
            stmt.setString(10, phone);
            stmt.setString(11, gender);
            stmt.setString(12, country);
            stmt.setString(13, state);
            stmt.setString(14, city);
            stmt.setString(15, zipCode);
            stmt.setBlob(16, inputStreamCover);
            stmt.registerOutParameter(17, java.sql.Types.INTEGER);
            //stmt.setString(17, password);
            System.out.println(userName+"\n"+password+"\n"+birthDate+"\n"+birthMonth+"\n"+birthYear+"\n"+gender+"\n"+firstName+"\n"+lastName+"\n"+email+"\n"+phone+"\n"+country+"\n"+city+"\n"+state+"\n"+zipCode+"\n");
            stmt.executeUpdate();
            long count = stmt.getLong(17);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int createPost(String posterId, String contents, String visibility){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_POST(?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, posterId);
            stmt.setString(2, contents);
            stmt.setString(3, visibility);
            System.out.println(posterId+"\n\n"+contents+"\n\n\n"+visibility);

            stmt.registerOutParameter(4, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(4);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int createComment(String commenterId, String contents, String postId){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_COMMENT(?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, commenterId);
            stmt.setString(2, postId);
            stmt.setString(3, contents);
            System.out.println(commenterId+"\n\n"+contents+"\n\n\n"+postId);

            stmt.registerOutParameter(4, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(4);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Posts> getPosts(String userId){
        ArrayList<Posts>posts = new ArrayList<Posts>();
        String query = "SELECT POSTID, CONTENT, LIKES, VISIBILITY, POSTERID \n" +
                "FROM POSTS P\n" +
                "WHERE POSTERID = ? OR VISIBILITY = 'Everyone' OR (? IN \n" +
                "\t(SELECT USER2ID userid\n" +
                "\t\tFROM FRIEND_TABLE \n" +
                "\t\tWHERE P.POSTERID = USER1ID\n" +
                "\tUNION\n" +
                "\t\tSELECT USER1ID userid\n" +
                "\t\tFROM FRIEND_TABLE \n" +
                "\t\tWHERE P.POSTERID = USER2ID\n" +
                "\t)\n" +
                "\t\n" +
                "\n" +
                ")\n" +
                "ORDER BY DATETIME DESC\n";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                System.out.println("here is fine");
                System.out.println("");
                String postId = rs.getString("POSTID");
                String posterId = rs.getString("POSTERID");
                String contents = rs.getString("CONTENT");
                String visible = rs.getString("VISIBILITY");
                int likes = rs.getInt("LIKES");

                Posts trans = new Posts(postId,posterId,contents,likes,visible);
                ArrayList<Comments> coms = getComments(postId);
                if(coms != null && !coms.isEmpty()){
                    trans.setComments(coms);
                }
                posts.add(trans);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return posts;
    }

    public  ArrayList<Comments> getComments(String postId){
        ArrayList<Comments> comments = new ArrayList<>();
        String query = "SELECT COMMENTID, LIKES, CONTENT, COMMENTERID, POSTID\n" +
                "FROM COMMENTS\n" +
                "WHERE POSTID = ? ORDER BY DATETIME ASC";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, postId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
               // System.out.println("here is fine");
               // System.out.println("");
                String commentId = rs.getString("COMMENTID");
                String commenterId = rs.getString("COMMENTERID");
                String contents = rs.getString("CONTENT");
                String post = rs.getString("POSTID");
                int likes = rs.getInt("LIKES");

                Comments comment = new Comments(commentId, commenterId, postId, contents);
                comment.setLikes(likes);
                comments.add(comment);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return comments;
    }


    public ArrayList<Person> getUsersByName(String name, String id){
        ArrayList<Person> persons = new ArrayList<>();

        String query = "SELECT USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "FROM USERS\n" +
                "WHERE UPPER(USERNAME) LIKE ? OR UPPER(FIRSTNAME) LIKE ? OR UPPER(LASTNAME) LIKE ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, name);
            stmt.setString(3, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                System.out.println("here is fine");
                //System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                //HttpSession session = request.getSession();

                Person p = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                p.setProfilePicId(picId);
                if(isFriend(userId, id)){
                    p.setFriendStatus("Friend");
                }
                else{
                    p.setFriendStatus("Not Friend");
                    String query2 = "SELECT STATUS\n" +
                            "FROM  FRIEND_REQUEST_TABLE\n" +
                            "WHERE (USER1ID = ? AND USER2ID = ?) OR (USER1ID = ? AND USER2ID = ?)";
                    try {
                        System.out.println(id + "\n\n\nrejected/pending\n\n\n "+userId);
                            PreparedStatement stmt2 = conn.prepareStatement(query2);
                            stmt2.setString(1, id);
                            stmt2.setString(4, id);
                            stmt2.setString(2, userId);
                            stmt2.setString(3, userId);
                            ResultSet rs2 = stmt2.executeQuery();
                            while(rs2.next()){
                                String status = rs2.getString("STATUS");
                                System.out.println(id + "\n\n\nrejected/pending\n\n\n "+userId+status);
                                p.setFriendReqStatus(status);
                                break;
                            }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                persons.add(p);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return persons;
    }

    public  boolean notInvited(String eventId, String userId){
        String query = "SELECT USERID\n" +
                "\tFROM EVENT_INVITE_USER\n" +
                "\tWHERE EVENTID = ? AND USERID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            //System.out.println();
            stmt.setString(1,eventId);
            stmt.setString(2,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return false;
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        //return true;
    }

    public ArrayList<Event> eventsInvitedTo(String userId){
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT EVENTID, EVENTNAME, EVENTTYPE, DESCRIPTION, CREATORID, EVENTDATE, EVENTMONTH, EVENTYEAR, EVENTHOUR, EVENTMINUTE, COUNTRY, CITY, ROADNO, BUILDINGNO, JOINSTATUS\n" +
                "FROM EVENTS JOIN EVENT_INVITE_USER USING (EVENTID)\n" +
                "WHERE USERID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String eventId = rs.getString("EVENTID");
                String eventName = rs.getString("EVENTNAME");
                String eventType = rs.getString("EVENTTYPE");
                String description = rs.getString("DESCRIPTION");
                String eventDate = rs.getString("EVENTDATE");
                String eventMonth = rs.getString("EVENTMONTH");
                String eventYear = rs.getString("EVENTYEAR");
                String eventHour = rs.getString("EVENTHOUR");
                String eventMinute = rs.getString("EVENTMINUTE");
                String country = rs.getString("COUNTRY");
                String city = rs.getString("CITY");
                String roadNo = rs.getString("ROADNO");
                String buildingNo = rs.getString("BUILDINGNO");
                String creatorId = rs.getString("CREATORID");
                String joinstatus = rs.getString("JOINSTATUS");
                Event event = new Event(eventName, eventId, eventType,description, eventDate, eventMonth, eventYear, eventHour, eventMinute,country,city,roadNo,buildingNo);
                event.setCreatorId(creatorId);
                event.setJoinStatus(joinstatus);
                ArrayList<Person>coming = friendGoingToEvent(eventId,userId);
                event.setComing(coming);
                events.add(event);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return events;
    }

    public ArrayList<Event> getMyEvents(String userId){
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT E.EVENTID, EVENTNAME, EVENTTYPE, DESCRIPTION, CREATORID, EVENTDATE, EVENTMONTH, EVENTYEAR, EVENTHOUR, EVENTMINUTE, COUNTRY, CITY, ROADNO, BUILDINGNO \n" +
                "FROM USER_JOIN_EVENT UJ JOIN EVENTS E ON (UJ.EVENTID = E.EVENTID)\n" +
                "WHERE UJ.USERID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1,userId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String eventId = rs.getString("EVENTID");
                String eventName = rs.getString("EVENTNAME");
                String eventType = rs.getString("EVENTTYPE");
                String description = rs.getString("DESCRIPTION");
                String eventDate = rs.getString("EVENTDATE");
                String eventMonth = rs.getString("EVENTMONTH");
                String eventYear = rs.getString("EVENTYEAR");
                String eventHour = rs.getString("EVENTHOUR");
                String eventMinute = rs.getString("EVENTMINUTE");
                String country = rs.getString("COUNTRY");
                String city = rs.getString("CITY");
                String roadNo = rs.getString("ROADNO");
                String buildingNo = rs.getString("BUILDINGNO");
                String creatorId = rs.getString("CREATORID");

                Event event = new Event(eventName, eventId, eventType,description, eventDate, eventMonth, eventYear, eventHour, eventMinute,country,city,roadNo,buildingNo);
                event.setCreatorId(creatorId);

                events.add(event);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return events;
    }

    public String getEventJoinStatus(String eventId, String userId){
        String query ="SELECT EVENTID\n" +
                "FROM USER_JOIN_EVENT\n" +
                "WHERE USERID=? AND EVENTID=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            stmt.setString(2,eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                return "GOING";
            }
            return "NOT INVITED/NOT GOING";
        }
        catch (SQLException e){
            e.printStackTrace();
            return "NOT INVITED/NOT GOING";
        }
    }

    public ArrayList<Person> friendGoingToEvent(String eventId, String userId){
        ArrayList<Person>friends = new ArrayList<>();
        String query = "SELECT US.USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "FROM USERS US JOIN USER_JOIN_EVENT UE ON (US.USERID = UE.USERID) \n" +
                "WHERE UE.EVENTID = ? AND UE.USERID IN (SELECT USER2ID user_id\n" +
                "\t\tFROM FRIEND_TABLE \n" +
                "\t\tWHERE USER1ID = ?\n" +
                "\tUNION\n" +
                "\t\tSELECT USER1ID user_id\n" +
                "\t\tFROM FRIEND_TABLE \n" +
                "\t\tWHERE USER2ID = ?\n" +
                "\t)";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, eventId);
            stmt.setString(2, userId);
            stmt.setString(3, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                Person user = new Person(id, userName, firstName, lastName);
                String picId = getProfilePicId(id);
                user.setProfilePicId(picId);
                friends.add(user);

            }
        }
        catch (SQLException e){

        }
        return  friends;
    }


    public ArrayList<Person> friendsInvited(String eventId){
        ArrayList<Person> friends = new ArrayList<>();
        String query = "SELECT U.USERID,U.USERNAME,U.FIRSTNAME, U.LASTNAME, E.JOINSTATUS\n" +
                "FROM EVENT_INVITE_USER E JOIN USERS U ON (E.USERID = U.USERID)\n" +
                "WHERE E.EVENTID = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,eventId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String eventJoinStatus = rs.getString("JOINSTATUS");

                Person person = new Person(userId,userName,firstName,lastName);
                person.setEventJoinStatus(eventJoinStatus);
                String picId = getProfilePicId(userId);
                person.setProfilePicId(picId);
                friends.add(person);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }



        return friends;
    }

    public ArrayList<Event> eventsCreated(String userId){
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT EVENTID, EVENTNAME, EVENTTYPE, DESCRIPTION, EVENTDATE, EVENTMONTH, EVENTYEAR, EVENTHOUR, EVENTMINUTE, COUNTRY, CITY, ROADNO, BUILDINGNO\n" +
                "FROM EVENTS\n" +
                "WHERE CREATORID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println();
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String eventId = rs.getString("EVENTID");
                String eventName = rs.getString("EVENTNAME");
                String eventType = rs.getString("EVENTTYPE");
                String description = rs.getString("DESCRIPTION");
                String eventDate = rs.getString("EVENTDATE");
                String eventMonth = rs.getString("EVENTMONTH");
                String eventYear = rs.getString("EVENTYEAR");
                String eventHour = rs.getString("EVENTHOUR");
                String eventMinute = rs.getString("EVENTMINUTE");
                String country = rs.getString("COUNTRY");
                String city = rs.getString("CITY");
                String roadNo = rs.getString("ROADNO");
                String buildingNo = rs.getString("BUILDINGNO");

                Event event = new Event(eventName, eventId, eventType,description, eventDate, eventMonth, eventYear, eventHour, eventMinute,country,city,roadNo,buildingNo);
                event.setCreatorId(userId);
                ArrayList<Person> invitedGuests = friendsInvited(eventId);
                event.setInvitedGuests(invitedGuests);
                events.add(event);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return events;
    }

    public int inviteFriendToEvent(String eventId, String userId){
        try
        {
            String insertCommand = "{CALL PROC_EVENT_INVITE_FRIENDS(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, eventId);
            stmt.setString(2, userId);
            System.out.println(eventId+"\n\n"+userId+"\n\n\n");

            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(3);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateJoinStatus(String eventId, String userId, String joinStatus){

        try{
            String updateCommand = "UPDATE EVENT_INVITE_USER\n" +
                    "SET JOINSTATUS = ?\n" +
                    "WHERE EVENTID= ? AND USERID = ?";
            PreparedStatement stmt = conn.prepareStatement(updateCommand);
            stmt.setString(1,joinStatus);
            stmt.setString(2,eventId);
            stmt.setString(3,userId);

            int count = stmt.executeUpdate();
            return count;
        }
        catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }


    public ArrayList<Person> getFriendLIst(String user){
        ArrayList<Person> friends = new ArrayList<>();
        String query = "SELECT USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "\t\tFROM FRIEND_TABLE FT JOIN USERS UR ON (FT.USER2ID = UR.USERID)\n" +
                "\t\tWHERE  FT.USER1ID = ?\n" +
                "\tUNION\n" +
                "\t\tSELECT USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "\t\tFROM FRIEND_TABLE  FT JOIN USERS UR ON (FT.USER1ID = UR.USERID)\n" +
                "\t\tWHERE FT.USER2ID = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, user);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
               // System.out.println("here is fine");
                //System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                //HttpSession session = request.getSession();

                Person p = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                p.setProfilePicId(picId);
                p.setFriendStatus("friend");

                friends.add(p);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return friends;
    }


    public ArrayList<Person> getSentFriendRequests(String id){
        ArrayList<Person> persons = new ArrayList<>();

        String query = "SELECT USERID, USERNAME, FIRSTNAME, LASTNAME, STATUS\n" +
                "FROM USERS UR JOIN FRIEND_REQUEST_TABLE FT ON (UR.USERID = FT.USER2ID)\n" +
                "WHERE USER1ID = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                System.out.println("here is fine");
                //System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String status = rs.getString("STATUS");
                //HttpSession session = request.getSession();

                Person person = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                person.setProfilePicId(picId);
                person.setFriendReqStatus(status);
                persons.add(person);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return persons;
    }

    public ArrayList<Person> getRecievedFriendRequests(String id){
        ArrayList<Person> persons = new ArrayList<>();

        String query = "SELECT USERID, USERNAME, FIRSTNAME, LASTNAME, STATUS\n" +
                "FROM USERS UR JOIN FRIEND_REQUEST_TABLE FT ON (UR.USERID = FT.USER1ID)\n" +
                "WHERE USER2ID = ? AND STATUS <> 'rejected'";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                System.out.println("Hehehehe!!!");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String status = rs.getString("STATUS");
                //HttpSession session = request.getSession();

                Person person = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                person.setProfilePicId(picId);
                person.setFriendReqStatus(status);
                persons.add(person);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return persons;
    }

    public boolean isFriend(String user1, String user2){
        boolean friend = false;
        try
        {
            String insertCommand = "{CALL PROC_IS_FRIEND(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, user1);
            stmt.setString(2, user2);


            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(3);
            if(count > 0) friend = true;
            return friend;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return friend;
        }

    }



    public Person getUserById(String id){
        Person user = new Person();
        String query = "SELECT USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "FROM USERS\n" +
                "WHERE USERID = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                //System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");


                Person p = new Person(userId,userName,firstName,lastName);

                user = p;
                String picId = getProfilePicId(userId);
                user.setProfilePicId(picId);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public String getName(String id){
        String name="";
        String query = "SELECT USERNAME FROM USERS WHERE USERID = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {

                name = rs.getString("USERNAME");


            }
            return name;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return  "";
        }


    }




    public int commentLikeIncrement(String commentId){

        String query = "UPDATE COMMENTS\n" +
                "SET LIKES = LIKES+1\n" +
                "WHERE COMMENTID = ?";
        System.out.println("comment like");
        try {
            System.out.println("comment like");
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, commentId);
            int ans = stmt.executeUpdate();
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int postLikeIncrement(String postId){

        String query = "UPDATE POSTS\n" +
                "SET LIKES = LIKES+1\n" +
                "WHERE POSTID = ?";
        System.out.println("POST like");
        try {
            //System.out.println("comment like");
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, postId);
            int ans = stmt.executeUpdate();
            System.out.println("post like after\n\n\n" +ans+"\n");
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }






    public int sendFriendRequest(String user1, String user2){
        //int count = 0;
        try
        {
            String insertCommand = "{CALL PROC_SEND_REQUEST(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, user1);
            stmt.setString(2, user2);


            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(3);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }


    }
    public int acceptDenyRequest(String user1, String user2, String aod){
        try
        {
            String insertCommand;

           if(aod.compareTo("accept") == 0) {
               insertCommand = "{CALL PROC_ACCEPT_FRIEND(?,?,?)}";
               System.out.println(aod+"\n\n\n"+user1);
               CallableStatement stmt = conn.prepareCall(insertCommand);
               stmt.setString(1, user1);
               stmt.setString(2, user2);


               stmt.registerOutParameter(3, java.sql.Types.INTEGER);
               stmt.executeUpdate();
               int count = stmt.getInt(3);
               return count;
           }
           else if(aod.compareTo("reject") == 0){
               String query = "\n" +
                       "UPDATE FRIEND_REQUEST_TABLE \n" +
                       "SET STATUS = 'rejected'\n" +
                       "WHERE USER1ID = ? AND USER2ID = ?";
               PreparedStatement stmt = conn.prepareStatement(query);
               stmt.setString(1, user1);
               stmt.setString(2, user2);
               int count = stmt.executeUpdate();
               return  count;
           }
           else return  0;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }


    public Person existUser(String userName,String password)
    {
        try
        {
            String query = "select USERID, USERNAME, FIRSTNAME, LASTNAME from USERS where USERNAME = ? and PASSWORD = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println(userName+"\n"+password);
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            Person person;
            if(rs.next())
            {
                String userID = rs.getString("userID");
                //String userName1 = rs.getString("username");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                person = new Person(userID,userName,firstName,lastName);
                String picId = getProfilePicId(userID);
                person.setProfilePicId(picId);
                System.out.println("\n\n\n"+userID+"\n\n\n\n"+userID);
                return person;
            }
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }


    }


    //Create Event

    public ArrayList<Event> getMyEventsInCity(String userId){
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT DISTINCT E.EVENTID, EVENTNAME, EVENTTYPE, DESCRIPTION, CREATORID, EVENTDATE, EVENTMONTH, EVENTYEAR, EVENTHOUR, EVENTMINUTE, COUNTRY, CITY, ROADNO, BUILDINGNO\n" +
                "FROM USER_JOIN_EVENT UJ JOIN EVENTS E ON (UJ.EVENTID = E.EVENTID)\n" +
                "WHERE E.CITY = (SELECT U.CITY FROM USERS U WHERE U.USERID = ?) AND UJ.USERID IN (SELECT USER2ID userid\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM FRIEND_TABLE \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE USER1ID=?\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tUNION\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSELECT USER1ID userid\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM FRIEND_TABLE \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE USER2ID=?)";


        try {
           PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1,userId);
            stmt.setString(2,userId);
            stmt.setString(3,userId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println("");
                String eventId = rs.getString("EVENTID");
                String eventName = rs.getString("EVENTNAME");
                String eventType = rs.getString("EVENTTYPE");
                String description = rs.getString("DESCRIPTION");
                String eventDate = rs.getString("EVENTDATE");
                String eventMonth = rs.getString("EVENTMONTH");
                String eventYear = rs.getString("EVENTYEAR");
                String eventHour = rs.getString("EVENTHOUR");
                String eventMinute = rs.getString("EVENTMINUTE");
                String country = rs.getString("COUNTRY");
                String city = rs.getString("CITY");
                String roadNo = rs.getString("ROADNO");
                String buildingNo = rs.getString("BUILDINGNO");
                String creatorId = rs.getString("CREATORID");
                ArrayList<Person> friends = friendGoingToEvent(eventId,userId);
                Event event = new Event(eventName, eventId, eventType,description, eventDate, eventMonth, eventYear, eventHour, eventMinute,country,city,roadNo,buildingNo);
                event.setCreatorId(creatorId);
                event.setComing(friends);

                events.add(event);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return events;
    }


    public int createEvent(Event event){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_EVENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);

            stmt.setString(1, event.getEventName());
            stmt.setString(2, event.getEventType());
            stmt.setString(3, event.getDescription());
            stmt.setString(4, event.getEventDate());
            stmt.setString(5, event.getEventMonth());
            stmt.setString(6, event.getEventYear());
            stmt.setString(7, event.getEventHour());
            stmt.setString(8, event.getEventMinute());
            stmt.setString(9, event.getCountry());
            stmt.setString(10, event.getCity());
            stmt.setString(11, event.getRoadNo());
            stmt.setString(12, event.getBuildingNo());
            stmt.setString(13, event.getCreatorId());

            stmt.registerOutParameter(14, java.sql.Types.INTEGER);
            //stmt.setString(17, password);
            //System.out.println(userName+"\n"+password+"\n"+birthDate+"\n"+birthMonth+"\n"+birthYear+"\n"+gender+"\n"+firstName+"\n"+lastName+"\n"+email+"\n"+phone+"\n"+country+"\n"+city+"\n"+state+"\n"+zipCode+"\n");

            stmt.executeUpdate();
            int count = stmt.getInt(14);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    // birthdays this month
    public ArrayList<Person> getBirthdaysThisMonth(String userId){
        ArrayList<Person> birthdaysThisMonth = new ArrayList<>();
        String query = "SELECT US.USERID, FIRSTNAME, LASTNAME, USERNAME, BIRTHDATE, BIRTHMONTH\n" +
                "FROM USERS US\n" +
                "WHERE BIRTHMONTH = TO_CHAR(SYSDATE, 'MM') AND  US.USERID IN (SELECT USER2ID user_id\n" +
                "\t\tFROM FRIEND_TABLE \n" +
                "\t\tWHERE USER1ID = ?\n" +
                "\tUNION\n" +
                "\t\tSELECT USER1ID user_id\n" +
                "\t\tFROM FRIEND_TABLE \n" +
                "\t\tWHERE USER2ID = ?\n" +
                "\t) ";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            stmt.setString(2,userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String friendId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String birthDay = rs.getString("BIRTHDATE");
                String birthMonth = rs.getString("BIRTHMONTH");
                Person friend = new Person(friendId,userName,firstName,lastName);
                String picId = getProfilePicId(friendId);
                friend.setProfilePicId(picId);
                friend.setBirthDay(birthDay);
                friend.setBirthMonth(birthMonth);
                birthdaysThisMonth.add(friend);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return birthdaysThisMonth;
    }


    // Message functions
    public int createNewThread(String threadName, String userName){
        try
        {
            String insertCommand = "{CALL PROC_NEW_THREAD(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, threadName);
            stmt.setString(2, userName);



            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int threadId=stmt.getInt(3);
            return threadId;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    public int createNewMessage(String threadId, String userId, String content){
        try
        {
            String insertCommand = "{CALL PROC_NEW_MESSAGE(?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, threadId);
            stmt.setString(2, userId);
            stmt.setString(3, content);



            stmt.registerOutParameter(4, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(4);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }

    }

    public int addFriendsToThread(String threadId, String friendId){
        try
        {
            String insertCommand = "{CALL PROC_ADD_TO_THREAD(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);

            stmt.setString(1, threadId);
            stmt.setString(2, friendId);



            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(3);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isFriendInThread(String friendId, String threadId){

        String query = "SELECT USERID\n" +
                "FROM USER_MSG_THREAD\n" +
                "WHERE USERID = ? AND THREADID = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,friendId);
            stmt.setString(2,threadId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return  true;
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }



    }

    public ArrayList<MessageThread> getThreadsById(String userId){
        ArrayList<MessageThread>threads = new ArrayList<>();
        String query="SELECT THREADID, THREADNAME\n" +
                "FROM MESSAGE_THREAD JOIN USER_MSG_THREAD USING(THREADID)\n" +
                "WHERE USERID = ?" +
                "ORDER BY STARTDATE DESC";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String threadId = rs.getString("THREADID");
                String threadName = rs.getString("THREADNAME");
                MessageThread mt = new MessageThread(threadId,threadName);
                threads.add(mt);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return threads;
    }

    public ArrayList<Messages>getMessagesByThread(String threadId){
            ArrayList<Messages> messages= new ArrayList<>();
            String query = "SELECT MESSAGEID, SENDERID, MESSAGECONTENT, USERNAME, FIRSTNAME, LASTNAME\n" +
                    "FROM MESSAGES MG JOIN USERS UR ON (MG.SENDERID = UR.USERID)\n" +
                    "WHERE MG.THREADID = ?" +
                    "ORDER BY DATETIME ASC";
            try{
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,threadId);

                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    String messageId = rs.getString("MESSAGEID");
                    String senderId = rs.getString("SENDERID");
                    String content = rs.getString("MESSAGECONTENT");
                    String userName = rs.getString("USERNAME");
                    String firstName = rs.getString("FIRSTNAME");
                    String lastName = rs.getString("LASTNAME");

                    Person sender = new Person(senderId,userName,firstName,lastName);
                    String picId = getProfilePicId(senderId);
                    sender.setProfilePicId(picId);
                    Messages msg = new Messages(messageId,threadId,sender,content);
                    messages.add(msg);
                }

            }
            catch (SQLException e){
                e.printStackTrace();

            }
            return messages;
    }

    public ArrayList<Person>getParticipantsByThread(String threadId){
        ArrayList<Person> participants= new ArrayList<>();
        String query = "SELECT USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "FROM USER_MSG_THREAD JOIN USERS USING(USERID)\n" +
                "WHERE THREADID = ?" +
                "ORDER BY JOINDATE ASC";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,threadId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                System.out.println("");
                String lastName = rs.getString("LASTNAME");

                Person user = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                user.setProfilePicId(picId);
                participants.add(user);
            }

        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return participants;
    }



    // end Message functions

    //start group functions

    public String getGroupNameByPost(String postId){
        String query = "SELECT GROUPNAME\n" +
                "FROM GROUP_POSTS JOIN GROUPS USING(GROUPID)\n" +
                "WHERE POSTID = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String groupName = rs.getString("GROUPNAME");
                return groupName;
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }



    public ArrayList<Group> getGroupsByUser(String userId){
        ArrayList<Group> groups = new ArrayList<>();
        String query="SELECT G.GROUPID, GROUPNAME, DESCRIPTION, CREATORID \n" +
                "FROM USER_TO_GROUPS UG JOIN GROUPS G ON (UG.GROUPID = G.GROUPID)  \n" +
                "WHERE UG.USERID = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String groupId = rs.getString("GROUPID");
                String groupName = rs.getString("GROUPNAME");
                String description = rs.getString("DESCRIPTION");
                String creatorId = rs.getString("CREATORID");

                Group group = new Group(groupId, groupName, description, creatorId);
                groups.add(group);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return groups;
    }

    public int addMembersToGroup(String userId, String groupId, String memberType){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_USER_GROUP(?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);

            stmt.setString(1, userId);
            stmt.setString(2, groupId);
            stmt.setString(3, memberType);



            stmt.registerOutParameter(4, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(4);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Person> membersInGroup(String groupID){
        ArrayList<Person>person = new ArrayList<Person>();
        String query = "SELECT DISTINCT U.userId, U.userName, U.firstName, U.lastName, G.memberType\n" +
                "FROM USER_TO_GROUPS G JOIN USERS U ON (U.userId = G.userId)\n" +
                "WHERE G.groupId = ?";



        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, groupID);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                //System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String memberType = rs.getString("MEMBERTYPE");

                Person trans = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                trans.setProfilePicId(picId);
                trans.setMemberType(memberType);
                person.add(trans);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return person;
    }


    public int createGroupPost(String posterId, String contents, String visibility, String groupId){
        try
        {
            String insertCommand = "{CALL INSERT_GROUP_POSTS(?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, posterId);
            stmt.setString(2, contents);
            stmt.setString(3, visibility);
            stmt.setString(4, groupId);
            System.out.println(posterId+"\n\n"+contents+"\n\n\n"+visibility+"\n\n\n"+groupId);

            stmt.registerOutParameter(5, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(5);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }



    public int removeGroupMember(String userId, String groupId){
        try
        {
            String insertCommand = "{CALL REMOVE_GROUP_MEMBER(?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, userId);
            stmt.setString(2, groupId);
            System.out.println(userId + "\n\n" + groupId);


            stmt.executeUpdate();

            return 1;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }



    public ArrayList<Posts> showGroupPosts(String groupId){
        ArrayList<Posts>posts = new ArrayList<Posts>();
        String query = "SELECT P.postID, content, likes, visibility, posterID\n" +
                "FROM POSTS P JOIN GROUP_POSTS G ON (P.POSTID = G.POSTID)\n" +
                "WHERE G.GROUPID = ?\n" +
                "ORDER BY DATETIME DESC";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, groupId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
               // System.out.println("");
                String postId = rs.getString("POSTID");
                String posterId = rs.getString("POSTERID");
                System.out.println("");
                String contents = rs.getString("CONTENT");
                String visible = rs.getString("VISIBILITY");
                int likes = rs.getInt("LIKES");

                Posts trans = new Posts(postId,posterId,contents,likes,visible);
                ArrayList<Comments> coms = getComments(postId);
                if(coms != null && !coms.isEmpty()){
                    trans.setComments(coms);
                }
                posts.add(trans);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return posts;
    }

    public int createGroup(String gName, String description, String creatorId){
        try
        {
            String insertCommand = "{CALL PROC_NEW_GROUP(?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            System.out.println("");
            stmt.setString(1, gName);
            stmt.setString(2, description);
            stmt.setString(3, creatorId);



            stmt.registerOutParameter(4, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(4);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean notMember(String userId, String groupId){

        String query = "SELECT USERID\n" +
                "FROM USER_TO_GROUPS  \n" +
                "WHERE USERID = ? AND GROUPID = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, groupId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                System.out.println("");
                return false;
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return true;
        }

    }

    public  String getMemberType(String groupId, String userId){
        String type = "";
        String query ="SELECT MEMBERTYPE\n" +
                "FROM USER_TO_GROUPS\n" +
                "WHERE GROUPID = ? AND USERID = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, groupId);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                type = rs.getString("MEMBERTYPE");
                return type;
            }
            return type;
        }
        catch (SQLException e){
            e.printStackTrace();
            return type;
        }

    }



    public ArrayList<Posts> getPostsInAGroupFriends(String userId, String groupId){
        ArrayList<Posts>posts = new ArrayList<Posts>();
        String query = "SELECT P.POSTID, CONTENT, LIKES, VISIBILITY, POSTERID\n" +
                "FROM POSTS P JOIN GROUP_POSTS G ON(P.POSTID = G.POSTID)\n" +
                "WHERE G.GROUPID = ? AND POSTERID IN\n" +
                "                (SELECT USER2ID USER_ID\n" +
                "                FROM FRIEND_TABLE \n" +
                "               WHERE USER1ID = ?\n" +
                "              UNION\n" +
                "               SELECT USER1ID USER_ID\n" +
                "               FROM FRIEND_TABLE\n" +
                "               WHERE USER2ID = ?\n" +
                "                )\n" +
                "                \n" +
                "                ORDER BY DATETIME DESC\n";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, groupId);
            stmt.setString(2, userId);
            stmt.setString(3, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                //System.out.println("");
                String postId = rs.getString("POSTID");
                String posterId = rs.getString("POSTERID");
                String contents = rs.getString("CONTENT");
                System.out.println("");
                String visible = rs.getString("VISIBILITY");
                int likes = rs.getInt("LIKES");

                Posts trans = new Posts(postId,posterId,contents,likes,visible);
                ArrayList<Comments> coms = getComments(postId);
                if(coms != null && !coms.isEmpty()){
                    trans.setComments(coms);
                }
                posts.add(trans);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return posts;
    }


    public ArrayList<Person> friendsInGroup(String userID, String groupID){
        ArrayList<Person> users = new ArrayList<>();
        String query = "SELECT U.USERID, USERNAME, FIRSTNAME, LASTNAME, MEMBERTYPE\n" +
                "        FROM USERS U JOIN USER_TO_GROUPS G ON(U.USERID = G.USERID)\n" +
                "        WHERE G.GROUPID = ? AND U.USERID IN (SELECT USER2ID\n" +
                "        FROM FRIEND_TABLE\n" +
                "        WHERE USER1ID = ?\n" +
                "        UNION\n" +
                "        SELECT USER1ID\n" +
                "        FROM FRIEND_TABLE\n" +
                "        WHERE USER2ID = ?)";



        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, groupID);
            stmt.setString(2, userID);
            stmt.setString(3, userID);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String memberType = rs.getString("MEMBERTYPE");


                Person p = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                p.setProfilePicId(picId);
                p.setMemberType(memberType);
                users.add(p);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return users;
    }


    //end group functions

    //shared history

    public ArrayList<Posts> sharedHistory(String user1Id, String user2Id){
        ArrayList<Posts>posts = new ArrayList<Posts>();
        String query = "SELECT P.POSTID, POSTERID, CONTENT, VISIBILITY, LIKES\t\n" +
                "FROM POSTS P\n" +
                "WHERE (P.posterID = ? AND ? IN (SELECT commenterID\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM COMMENTS C\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE C.postID = P.postID))\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\tOR\n" +
                "\t\t\t(P.posterID = ? AND ? IN (SELECT commenterID\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM COMMENTS C\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE C.postID = P.postID))\n" +
                "ORDER BY DATETIME DESC\n";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user1Id);
            stmt.setString(2, user2Id);
            stmt.setString(3, user2Id);
            stmt.setString(4, user1Id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                System.out.println("");
                String postId = rs.getString("POSTID");
                String posterId = rs.getString("POSTERID");
                String contents = rs.getString("CONTENT");
                String visible = rs.getString("VISIBILITY");
                int likes = rs.getInt("LIKES");

                Posts trans = new Posts(postId,posterId,contents,likes,visible);
                ArrayList<Comments> coms = getComments(postId);
                if(coms != null && !coms.isEmpty()){
                    trans.setComments(coms);
                }
                posts.add(trans);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return posts;
    }


    // picture functions
    public String getProfilePicId(String userId){
        String query = "SELECT PICTUREID\n" +
                "FROM PICTURES\n" +
                "WHERE USERID = ? AND PICTURENAME = 'Profile Pic'";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("PICTUREID");
                return id;
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public InputStream getPicById(String id){
        InputStream image = null;
        String query = "SELECT PICTURE\n" +
                "FROM PICTURES\n" +
                "WHERE PICTUREID = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                image = rs.getBinaryStream("PICTURE");
                return image;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return image;
    }


    public ArrayList<Person> albumCollaboration(String user1Id){
        ArrayList<Person>person = new ArrayList<Person>();
        String query = "SELECT DISTINCT U.userID, U.userName, U.firstName, U.lastName\n" +
                "FROM USERS U JOIN ALBUMS A ON (A.creatorID = U.userID)\n" +
                "WHERE ? IN (SELECT AC.userID\n" +
                "\t\t\t\t\t\tFROM ALBUM_COLLABORATES_USER AC\n" +
                "\t\t\t\t\t\tWHERE\tAC.albumID = A.albumID)";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user1Id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                System.out.println("here is fine");
                System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");

                Person trans = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                trans.setProfilePicId(picId);
                person.add(trans);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return person;
    }


    public ArrayList<Picture> friendAlbum(String user1Id, String user2Id){
        ArrayList<Picture>picture = new ArrayList<Picture>();
        String query = "SELECT DISTINCT P.pictureID, P.pictureName, P.userID, P.albumID\n" +
                "FROM PICTURES P JOIN ALBUMS A ON (A.ALBUMID = P.ALBUMID)\n" +
                "WHERE A.ALBUMID IN (SELECT A.ALBUMID\n" +
                "\t\t\t\t\t\t\t\t\t\tFROM ALBUM_COLLABORATES_USER AC\n" +
                "\t\t\t\t\t\t\t\t\t\tWHERE A.CREATORID = ? AND AC.USERID = ?)\n" +
                "\t\t\tAND P.USERID = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user2Id);
            stmt.setString(2, user1Id);
            stmt.setString(3, user1Id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                System.out.println("");
                String pictureId = rs.getString("PICTUREID");
                String pictureName = rs.getString("PICTURENAME");
                String userId = rs.getString("USERID");
                String albumId = rs.getString("ALBUMID");

                Picture trans = new Picture(pictureId,pictureName,userId,albumId);

                picture.add(trans);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return picture;
    }

    public ArrayList<Album> getAlbumsByUser(String userId){
        ArrayList<Album>albums = new ArrayList<>();
        String query="SELECT ALBUMID, ALBUMNAME, CREATORID\n" +
                "FROM ALBUMS\n" +
                "WHERE CREATORID = ?\n" +
                "UNION\n" +
                "SELECT ALBUMID, ALBUMNAME, CREATORID\n" +
                "FROM ALBUMS JOIN ALBUM_COLLABORATES_USER USING(ALBUMID)\n" +
                "WHERE USERID = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            stmt.setString(2,userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String albumId = rs.getString("ALBUMID");
                String albumName = rs.getString("ALBUMNAME");
                String creatorId = rs.getString("CREATORID");
                Album album = new Album(albumId,albumName,creatorId);

                albums.add(album);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return albums;
    }

    public boolean notCollaborator(String userId, String albumId){
        String query = "SELECT ALBUMID\n" +
                "FROM ALBUM_COLLABORATES_USER\n" +
                "WHERE ALBUMID=? AND USERID = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,albumId);
            System.out.println("");
            statement.setString(2,userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                return false;
            }
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return true;
        }

    }

    public ArrayList<Person> collaboratorInAlbum(String albumID){
        ArrayList<Person>person = new ArrayList<Person>();
        String query = "SELECT U.USERID, USERNAME, FIRSTNAME, LASTNAME\n" +
                "FROM USERS U JOIN ALBUM_COLLABORATES_USER A ON (U.USERID = A.USERID)\n" +
                "WHERE A.ALBUMID = ?\n";



        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, albumID);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                System.out.println("");
                String userId = rs.getString("USERID");
                String userName = rs.getString("USERNAME");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");

                Person trans = new Person(userId,userName,firstName,lastName);
                String picId = getProfilePicId(userId);
                trans.setProfilePicId(picId);
                person.add(trans);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return person;
    }




    public ArrayList<Picture> picturesInAlbum(String albumID){
        ArrayList<Picture>picture = new ArrayList<Picture>();
        String query = "SELECT pictureID, pictureName, caption, userID, albumID\n" +
                "FROM PICTURES\n" +
                "WHERE albumID = ?";



        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, albumID);

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                //System.out.println("here is fine");
                //System.out.println("");
                String pictureId = rs.getString("PICTUREID");
                String pictureName = rs.getString("PICTURENAME");
                String userId = rs.getString("USERID");
                String albumId = rs.getString("ALBUMID");
                String caption = rs.getString("CAPTION");

                Picture trans = new Picture(pictureId,pictureName,userId,albumId);
                trans.setCaption(caption);
                picture.add(trans);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return picture;
    }



    public int createAlbum(String albumName, String creatorId){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_ALBUM(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, albumName);
            stmt.setString(2, creatorId);
            System.out.println(albumName+"\n\n"+creatorId);

            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(3);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }



    public int createPicture(String pictureName, String caption, String userId, String albumId, InputStream picture){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_PICTURE(?,?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, pictureName);
            stmt.setString(2, caption);
            stmt.setString(3, userId);
            stmt.setString(4, albumId);
            stmt.setBlob(5, picture);
            System.out.println(pictureName + "\n\n" + caption + "\n\n\n" + userId + "\n\n\n" + albumId);

            stmt.registerOutParameter(6, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(6);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }




    public int assignAlbumCollaborator(String albumId, String userId){
        try
        {
            String insertCommand = "{CALL PROC_INSERT_ALBUM_COLLABORATOR(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(insertCommand);
            stmt.setString(1, albumId);
            stmt.setString(2, userId);
            System.out.println(albumId+"\n\n"+userId);

            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();
            int count=stmt.getInt(3);
            return count;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }


    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
