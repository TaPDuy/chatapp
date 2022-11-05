package nhom12.chatapp.client.listener;

import java.awt.Container;
import java.io.IOException;

public interface MessageListener {
    
    public void sendGlobal(String msg) throws IOException;
    public void sendMessage(String msg);
    public void createGroup(String name) throws IOException;
    
    public void setChatView(Container view);
    public String getReceiverName();
    public void setReceiverName(String name);
    public void sendFindFriend(String key) throws IOException;
    public void sendFindGroup(String key) throws IOException;
    public void sendAddFriend(String receiverName) throws IOException;
    
    public void processNotification(int index) throws IOException;
    public void processUnfriend(int index) throws IOException;
    public void processViewProfile(int index) throws IOException;
    public void processJoinGroup(String groupName) throws IOException;
    
    public void updateFriends() throws IOException;



}
