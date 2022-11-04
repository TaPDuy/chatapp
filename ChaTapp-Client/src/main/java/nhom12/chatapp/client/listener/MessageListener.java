package nhom12.chatapp.client.listener;

import java.awt.Container;
import java.io.IOException;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;

public interface MessageListener {
    
    public void sendGlobal(String msg) throws IOException;
    public void sendMessage(String msg);
    public void createGroup(String name) throws IOException;
    
    public void setChatView(Container view);
    public String getReceiverName();
    public void setReceiverName(String name);
    public void sendDeleteFriend(User frienDel, String time) throws IOException;
    public void sendFindFriend(String key) throws IOException;
    public void sendAddFriend(String receiverName) throws IOException;
    public void sendConfirmAddFriend(int notId) throws IOException;
    public void sendDeleteNotification(Notification delNotification) throws IOException;
}
