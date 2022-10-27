package nhom12.chatapp.client.listener;

import java.io.IOException;
import nhom12.chatapp.model.User;

public interface MessageListener {
    
    public void sendGlobal(String msg) throws IOException;
    public void send(String msg, String receiverID) throws IOException;
}
