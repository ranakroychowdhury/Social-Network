package Models;

import java.sql.Date;

/**
 * Created by ASUS on 12/15/2016.
 */
public class Messages {
    String messageId;
    String threadId;
    Person sender;
    String contents;
    Date dateTime;

    public Messages(String messageId, String threadId, Person sender, String contents) {
        this.messageId = messageId;
        this.threadId = threadId;
        this.sender = sender;
        this.contents = contents;
    }

    public Messages(String messageId, String threadId, String contents) {
        this.messageId = messageId;
        this.threadId = threadId;
        this.contents = contents;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
