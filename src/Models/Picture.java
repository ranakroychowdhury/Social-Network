package Models;

/**
 * Created by ASUS on 12/16/2016.
 */
public class Picture {
    String pictureId;
    String pictureName;
    String likes;
    String caption;
    String userId;
    String albumId;


    public Picture(String pictureId, String pictureName, String userId, String albumId) {
        this.pictureId = pictureId;
        this.pictureName = pictureName;
        this.userId = userId;
        this.albumId = albumId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
