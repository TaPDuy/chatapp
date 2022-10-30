package nhom12.chatapp.model;

import java.util.Date;

public class Message {
    
    private int id;
    private String content;
    private User sender, receiver;
    private Group group;
    private Date timeSent;

    public Message() {
	this(0, "", null, null, null, new Date());
    }

    public Message(int id, String content, User sender, User receiver, Group group, Date timeSent) {
	this.id = id;
	this.content = content;
	this.sender = sender;
	this.receiver = receiver;
	this.group = group;
	this.timeSent = timeSent;
    }

    public int getId() {
	return id;
    }

    public Message setId(int id) {
	this.id = id;
	return this;
    }

    public String getContent() {
	return content;
    }

    public Message setContent(String content) {
	this.content = content;
	return this;
    }

    public User getSender() {
	return sender;
    }

    public Message setSender(User sender) {
	this.sender = sender;
	return this;
    }

    public User getReceiver() {
	return receiver;
    }

    public Message setReceiver(User receiver) {
	this.receiver = receiver;
	return this;
    }

    public Group getGroup() {
	return group;
    }

    public Message setGroup(Group group) {
	this.group = group;
	return this;
    }

    public Date getTimeSent() {
	return timeSent;
    }

    public Message setTimeSent(Date timeSent) {
	this.timeSent = timeSent;
	return this;
    }

    @Override
    public String toString() {
	return 
	    "(Message) -> {\n"
	    + "\tid: "		+ this.id		    + ",\n"
	    + "\tcontent: '"	+ this.content		    + "',\n" 
	    + "\tsender: "	+ this.sender.toString()    + ",\n"
	    + "\treceiver: "	+ this.receiver.toString()  + ",\n"
	    + "\tgroup: "	+ this.group.toString()	    + ",\n"
	    + "\ttimeSent: "	+ this.timeSent.toString()  + "\n}";
    }
}
