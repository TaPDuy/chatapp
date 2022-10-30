package nhom12.chatapp.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private List<User> userInsystem;
    private String receiverName;
    
    private HashMap<String, List<String>> loadedMessages;
    
    private ClientView view;

    public ChatClient(ServerConnection server) {
	this.server = server;
	this.user = new User();
	this.user.setViewName("guest");
	
	onlineList = new ArrayList<>();
        userInsystem = new ArrayList<>();
	loadedMessages = new HashMap<>();
        id = -1;
    }

    public void setView(ClientView view) {
	this.view = view;
    }
    
    @Override
    public void run() {

	try {
	    
	    String cmdLine;
	    while (true) {

		cmdLine = server.read();
		if(cmdLine==null)
		    break;
		
		System.out.println("[SERVER]: " + cmdLine);
		
		String[] cmdSplit = cmdLine.split(" ", 2);
		String cmd = cmdSplit[0];
		String argstr = cmdSplit[1];
		
		switch (cmd) {
		    case "set-user":
			this.user = (User) server.readObject();
                        System.out.println(user.getId());
                        view.setUser(user);
                        view.updateCombobox(onlineList);
			break;
		    case "update-online-list":
			onlineList = new ArrayList<>();
			String online = "";
			String[] onlineSplit = argstr.split("-");
			for (String onlineSplit1 : onlineSplit) {
			    if (!onlineSplit1.equals(this.user.getViewName()))
				onlineList.add("Client " + onlineSplit1);
			    online += "Client " + onlineSplit1 + " đang online\n";
			}	
			view.getTextArea2().setText(online);
			view.updateCombobox(onlineList);
			break;
//                    case "User-In-System":
//                        this.userInsystem = (List<User>) server.readObject();
//                        view.setTableUserSys(userInsystem);
//                        break;
		    case "display":
			String[] args = argstr.split(" ", 2);
			view.printMessage("[" + args[0] + "]: " + args[1]);
			break;
		    case "display-server":
			view.printMessage("[SERVER]: " + argstr);
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
    public void sendMessage(String msg) {
	try {
	    
	    if(receiverName != null) {
		view.printMessage("[You]: " + msg);
		server.write("msg " + receiverName + " " + msg);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void sendDeleteFriend(String idFriend) throws IOException {
        server.write("deletefriend " + idFriend);
    }

    @Override
    public void sendAddFriend(String nickName) throws IOException {
        server.write("addFriend " + nickName);
    }

}
