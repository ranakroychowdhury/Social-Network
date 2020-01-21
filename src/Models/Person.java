package Models;

/**
 * Created by ASUS on 12/13/2016.
 */
public class Person {
    String userId;
    String userName;
    String firstName;
    String lastName;
    String friendStatus;
    String friendReqStatus;
    String eventJoinStatus;
    String birthDay;
    String birthMonth;
    String memberType;
    String profilePicId;

    public String getProfilePicId() {
        return profilePicId;
    }

    public void setProfilePicId(String profilePicId) {
        this.profilePicId = profilePicId;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getEventJoinStatus() {
        return eventJoinStatus;
    }

    public void setEventJoinStatus(String eventJoinStatus) {
        this.eventJoinStatus = eventJoinStatus;
    }



    public Person() {
        friendReqStatus = "";
        friendStatus = "";
    }

    public Person(String userId, String userName, String firstName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.friendReqStatus = "";
        this.friendStatus = "";
    }

    public String getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getFriendReqStatus() {
        return friendReqStatus;
    }

    public void setFriendReqStatus(String friendReqStatus) {
        this.friendReqStatus = friendReqStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

