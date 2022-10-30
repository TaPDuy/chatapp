package nhom12.chatapp.client.listener;

import java.io.IOException;

public interface MessageListener {
    
    public void sendGlobal(String msg) throws IOException;
    public void send(String msg, String receiverID) throws IOException;
    public void sendDeleteFriend(String idFriend) throws IOException;
    public void sendAddFriend(String nickName) throws IOException;
}
