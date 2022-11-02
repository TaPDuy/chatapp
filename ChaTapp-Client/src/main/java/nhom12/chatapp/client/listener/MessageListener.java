package nhom12.chatapp.client.listener;

import java.awt.Container;
import java.io.IOException;

public interface MessageListener {
    
    public void sendGlobal(String msg) throws IOException;
    public void sendMessage(String msg);
    public void sendDeleteFriend(String idFriend) throws IOException;
    public void sendFindFriend(String key) throws IOException;
    public void sendAddFriend(int id) throws IOException;
    
    public void createGroup(String name) throws IOException;
    
    public void setChatView(Container view);
    public String getReceiverName();
    public void setReceiverName(String name);
}
