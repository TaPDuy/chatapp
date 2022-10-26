
package nhom12.chatapp.client.view;

import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.listener.WindowListener;

public interface GenericView {
    public void setMessageListener(MessageListener listener);
    public void setWindowListener(WindowListener listener);
}
