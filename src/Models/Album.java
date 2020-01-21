package Models;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/16/2016.
 */
public class Album {

    String albumId;
    String albumName;
    String creatorId;
    ArrayList<Picture> pics;
    ArrayList<Person> collaborater;


    public Album(String albumId, String albumName, String creatorId) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.creatorId = creatorId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<Picture> getPics() {
        return pics;
    }

    public void setPics(ArrayList<Picture> pics) {
        this.pics = pics;
    }

    public ArrayList<Person> getCollaborater() {
        return collaborater;
    }

    public void setCollaborater(ArrayList<Person> collaborater) {
        this.collaborater = collaborater;
    }
}
