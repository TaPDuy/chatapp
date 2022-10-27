package nhom12.chatapp.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.client.controller.LoginController;
import nhom12.chatapp.client.controller.WindowController;
import nhom12.chatapp.client.view.LoginFrm;

public class EntryPoint {
    
    public static void main(String[] args) {
	ServerConnection server = null;
	try {
	    server = new ServerConnection("localhost", 7777);
	} catch (IOException ex) {
	    Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	if(server != null) {
	    WindowController winCtrl = new WindowController(server);
	} else {
	    System.out.println("[ERROR]: Failed to connect to server.");
	}
	
    }
}
