package nhom12.chatapp.client.listener;

import java.io.IOException;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;

public interface MessageListener {
    
    public void sendGlobal(String msg) throws IOException;
    public void send(String msg, String receiverID) throws IOException;
    public void sendDeleteFriend(User frienDel, String time) throws IOException;
    public void sendFindFriend(String key) throws IOException;
    public void sendAddFriend(User userReceive, String time) throws IOException;
    public void sendConfirmAddFriend(User userSend, String time) throws IOException;
    public void sendDeleteNotification(Notification delNotification) throws IOException;
}
