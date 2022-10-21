package nhom12.chatapp.client;

import javax.swing.JOptionPane;
import nhom12.chatapp.client.view.LoginFrm;

public class EntryPoint {
    
    public static final String SERVER_NAME = "localhost";
    public static final int SERVER_PORT = 9999;
    
    public static void main(String[] args) {
	
	ChatClient client = new ChatClient();
	
	if(client.connect(SERVER_NAME, SERVER_PORT))
	    (new LoginFrm(client)).setVisible(true);
	else
	    JOptionPane.showMessageDialog(null, "Couldn't connect to server.", "Error!", JOptionPane.ERROR_MESSAGE);
	
    }
}
