package nhom12.chatapp.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.client.controller.ChatClient;
import nhom12.chatapp.client.controller.LoginController;
import nhom12.chatapp.client.view.ViewContainer;

public class Client {
    
    public static void main(String[] args) {
	ServerConnection server = null;
	try {
	    server = new ServerConnection("localhost", 7777);
	} catch (IOException ex) {
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	if(server != null) {
	    ViewContainer frame = new ViewContainer();
	    frame.setLoginListener(new LoginController(server));
	    
	    ChatClient chatCtrl = new ChatClient(server);
	    frame.setMessageListener(chatCtrl);
	    frame.setMessageLoop(chatCtrl);
	    
	    frame.switchToView("LoginFrm");
	    frame.setVisible(true);
	} else {
	    System.out.println("[ERROR]: Failed to connect to server.");
	}
	
    }
}
