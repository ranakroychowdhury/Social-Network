package Models;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/16/2016.
 */
public class Event {
    String eventName;
    String eventId;
    String eventType;
    String description;
    String eventDate;
    String eventMonth;
    String eventYear;
    String eventHour;
    String eventMinute;
    String country;
    String city;
    String roadNo;
    String buildingNo;
    String creatorId;
    String joinStatus;
    ArrayList<Person> invitedGuests;
    ArrayList<Person> coming;


    public Event(String eventName, String eventId, String eventType, String description, String eventDate, String eventMonth, String eventYear, String eventHour, String eventMinute, String country, String city, String roadNo, String buildingNo) {
        this.eventName = eventName;
        this.eventId = eventId;
        this.eventType = eventType;
        this.description = description;
        this.eventDate = eventDate;
        this.eventMonth = eventMonth;
        this.eventYear = eventYear;
        this.eventHour = eventHour;
        this.eventMinute = eventMinute;
        this.country = country;
        this.city = city;
        this.roadNo = roadNo;
        this.buildingNo = buildingNo;
    }

    public Event(String eventName, String eventType, String description, String eventDate, String eventMonth, String eventYear, String eventHour, String eventMinute, String country, String city, String roadNo, String buildingNo) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.description = description;
        this.eventDate = eventDate;
        this.eventMonth = eventMonth;
        this.eventYear = eventYear;
        this.eventHour = eventHour;
        this.eventMinute = eventMinute;
        this.country = country;
        this.city = city;
        this.roadNo = roadNo;
        this.buildingNo = buildingNo;
    }

    public String getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getEventYear() {
        return eventYear;
    }

    public void setEventYear(String eventYear) {
        this.eventYear = eventYear;
    }

    public String getEventHour() {
        return eventHour;
    }

    public void setEventHour(String eventHour) {
        this.eventHour = eventHour;
    }

    public String getEventMinute() {
        return eventMinute;
    }

    public void setEventMinute(String eventMinute) {
        this.eventMinute = eventMinute;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<Person> getInvitedGuests() {
        return invitedGuests;
    }

    public void setInvitedGuests(ArrayList<Person> invitedGuests) {
        this.invitedGuests = invitedGuests;
    }

    public ArrayList<Person> getComing() {
        return coming;
    }

    public void setComing(ArrayList<Person> coming) {
        this.coming = coming;
    }
}
