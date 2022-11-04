package nhom12.chatapp.model;

import java.io.Serializable;

public class Notification implements Serializable{
    
    private static final long serialVersionUID = 3L;
    private User userSend;
    private String content;
    private String timeDate;
    private String active;

    public User getUserSend() {
        return userSend;
    }

    public void setUserSend(User userSend) {
        this.userSend = userSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
