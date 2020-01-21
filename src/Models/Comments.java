package Models;

import Utilities.DatabaseConnection;

/**
 * Created by ASUS on 12/12/2016.
 */
public class Comments {
    String commentId;
    String commenterId;
    String postId;
    String contents;
    int likes;

    public Comments() {
    }

    public Comments(String commentId, String commenterId, String postId, String contents) {
        this.commentId = commentId;
        this.commenterId = commenterId;
        this.postId = postId;
        this.contents = contents;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommenterName(){
        DatabaseConnection db = new DatabaseConnection();
        String name = db.getName(commenterId);
        db.close();
        return name;
    }
    public String getCommenterPicId(){
        DatabaseConnection db = new DatabaseConnection();
        String name = db.getProfilePicId(commenterId);
        db.close();
        return name;
    }


    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
}
