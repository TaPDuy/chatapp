package nhom12.chatapp.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import nhom12.chatapp.client.ServerConnection;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.view.ClientView;
import nhom12.chatapp.model.User;

public class ChatClient extends Thread implements MessageListener {

    private final ServerConnection server;
    
    private List<String> onlineList;
    private int id;
    private User user;
    
    private ClientView view;

    public ChatClient(ServerConnection server) {
	this.server = server;
	this.user = new User();
	this.user.setViewName("guest");
	
	onlineList = new ArrayList<>();
        id = -1;
    }

    public void setView(ClientView view) {
	this.view = view;
    }
    
    @Override
    public void run() {

	try {
	    
	    String message;
	    while (true) {

		message = server.read();

		if(message==null)
		    break;
		
		System.out.println("[SERVER]: " + message);
		
		String[] messageSplit = message.split(" ", 2);
		
		switch (messageSplit[0]) {
		    case "set-user":
			this.user = (User) server.readObject();
			view.setTitle("Client " + this.user.getViewName());
			break;
		    case "update-online-list":
			onlineList = new ArrayList<>();
			String online = "";
			String[] onlineSplit = messageSplit[1].split("-");
			for (String onlineSplit1 : onlineSplit) {
			    if (!onlineSplit1.equals(this.user.getViewName()))
				onlineList.add("Client " + onlineSplit1);
			    online += "Client " + onlineSplit1 + " đang online\n";
			}	
			view.getTextArea2().setText(online);
			view.updateCombobox(onlineList);
			break;
		    case "display":
			String[] args = messageSplit[1].split(" ", 2);
			JTextArea txtArea = view.getTextArea1();
			txtArea.setText(txtArea.getText() + "[" + args[0] + "]: " + args[1] + "\n");
			txtArea.setCaretPosition(txtArea.getDocument().getLength());
			break;
		    case "display-global":
			txtArea = view.getTextArea1();
			txtArea.setText(txtArea.getText() + "[SERVER]: " + messageSplit[1] + "\n");
			txtArea.setCaretPosition(txtArea.getDocument().getLength());
			break;
		    default:
			break;
		}
	    }
	    
	    server.close();
	    
	} catch (UnknownHostException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	}

    }
    
    private void setID(int id){
        this.id = id;
    }

    @Override
    public void sendGlobal(String msg) throws IOException {
	server.write("msg-global " + msg);
    }

    @Override
    public void send(String msg, String receiver) throws IOException {
	server.write("msg " + receiver + " " + msg);
    }
}
