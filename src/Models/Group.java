package Models;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/16/2016.
 */
public class Group {
    String groupId;
    String groupName;
    String description;
    String creatorId;
    ArrayList<Person> member;
    ArrayList<Posts> groupPosts;

    public Group(String groupName, String description, String creatorId) {
        this.groupName = groupName;
        this.description = description;
        this.creatorId = creatorId;
    }

    public Group(String groupId, String groupName, String description, String creatorId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.creatorId = creatorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<Person> getMember() {
        return member;
    }

    public void setMember(ArrayList<Person> member) {
        this.member = member;
    }

    public ArrayList<Posts> getGroupPosts() {
        return groupPosts;
    }

    public void setGroupPosts(ArrayList<Posts> groupPosts) {
        this.groupPosts = groupPosts;
    }
}
