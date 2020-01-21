package Models;

import Utilities.DatabaseConnection;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/12/2016.
 */
public class Posts {
    String postId;
    String posterId;
    String contents;
    int likes;
    String visible;



    ArrayList<Comments> comments;


    public Posts() {
        this.posterId = "";
        this.contents = "";
        this.likes = 0;
        this.visible = "";
        this.postId = "";
    }

    public Posts(String postId, String posterId, String contents, int likes, String visible) {

        this.posterId = posterId;
        this.contents = contents;
        this.likes = likes;
        this.visible = visible;
        this.postId = postId;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public String getPosterId() {
        return posterId;
    }

    public String getPosterName(){
        DatabaseConnection db = new DatabaseConnection();
        String name = db.getName(posterId);
        db.close();
        return name;
    }

    public String getPosterPicId(){
        DatabaseConnection db = new DatabaseConnection();
        String name = db.getProfilePicId(posterId);
        db.close();
        return name;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }



}
