package nhom12.chatapp.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.client.view.LoginFrm;

public class EntryPoint {
    
    public static void main(String[] args) {
	LoginFrm login = new LoginFrm();
	ChatClient client = new ChatClient(login);
	login.setVisible(true);
	
	try {
	    client.connect("localhost", 7777);
	} catch (IOException ex) {
	    Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
	}
	client.start();
    }
}
