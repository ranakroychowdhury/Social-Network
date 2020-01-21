package Models;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by ASUS on 12/15/2016.
 */
public class MessageThread {
    String threadId;
    Date startDate;
    String threadName;
    ArrayList<Person>participants;
    ArrayList<Messages>messages;

    public MessageThread(String threadId, String threadName) {
        this.threadId = threadId;
        this.threadName = threadName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public MessageThread() {
    }

    public MessageThread(String threadId, Date startDate) {
        this.threadId = threadId;
        this.startDate = startDate;
    }

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }

    public MessageThread(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadId() {
        return threadId;
    }

    public ArrayList<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Person> participants) {
        this.participants = participants;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
