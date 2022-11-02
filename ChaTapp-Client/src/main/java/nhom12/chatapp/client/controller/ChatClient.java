package nhom12.chatapp.client.controller;

import java.awt.Container;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import nhom12.chatapp.client.ServerConnection;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.view.ClientView;
import nhom12.chatapp.model.User;

public class ChatClient implements MessageListener, Runnable {

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
	this.user.setUsername("guest");
	
	onlineList = new ArrayList<>();
        userInsystem = new ArrayList<>();
	loadedMessages = new HashMap<>();
        id = -1;
    }

    @Override
    public String getReceiverName() {
	return receiverName;
    }

    @Override
    public void setReceiverName(String name) {
	this.receiverName = name;
	if (receiverName != null)
	    view.setChatBoxTitle("Đang nhắn với " + (receiverName.charAt(0) == '#' ? "nhóm " + receiverName.substring(1) : receiverName));
	else
	    view.setChatBoxTitle("");
    }
    
    @Override
    public void setChatView(Container view) {
	this.view = (ClientView) view;
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
		String argstr = cmdSplit.length > 1 ? cmdSplit[1] : "";
		
		switch (cmd) {
		    case "set-user":
			this.user = (User) server.readObject();
                        view.setUser(user);
                        view.updateCombobox(onlineList);
			break;
		    case "update-online-list":
			onlineList = new ArrayList<>();
			String online = "";
			String[] onlineSplit = argstr.split("-");
			for (String onlineSplit1 : onlineSplit) {
			    if (!onlineSplit1.equals(this.user.getUsername()))
				onlineList.add("Client " + onlineSplit1);
			    online += "Client " + onlineSplit1 + " đang online\n";
			}	
			view.getTextArea2().setText(online);
			view.updateCombobox(onlineList);
			break;
                    case "notification-delete":
                        this.user = (User) server.readObject();
                        view.setUser(user);
                        view.updateCombobox(onlineList);
                        String[] meString = argstr.split(" ",2);
                        System.out.println(meString[0]);
                        if(meString[0].equals(user.getUsername())){
                            view.setTableNotification(meString[1]);
                        }
                        break;
                    case "notification-add":
                        this.user = (User) server.readObject();
                        view.setUser(user);
                        view.updateCombobox(onlineList);
                        //String[] meString = messageSplit[1].split(" ",2);
//                        System.out.println(meString[0]);
//                        if(meString[0].equals(user.getViewName())){
//                            view.setTableNotification(meString[1]);
//                        }
                        break;
                    case "User-In-System":
                        
                        this.userInsystem = (List<User>) server.readObject();
                        //System.out.println(userInsystem.get(0).getViewName());
                        view.setTableUserSys(userInsystem);
                        break;
		    case "display":
			String[] args = argstr.split(" ", 2);
			view.printMessage("[" + args[0] + "]: " + args[1]);
			break;
		    case "display-server":
			view.printMessage("[SERVER]: " + argstr);
			break;
		    case "group-created":
			JOptionPane.showMessageDialog(view, "Created group '" + argstr + "' successfully!", "Group created", JOptionPane.INFORMATION_MESSAGE);
			break;
		    case "group-existed":
			JOptionPane.showMessageDialog(view, "Group '" + argstr + "' has already been created!", "Group existed", JOptionPane.ERROR_MESSAGE);
			break;
		    case "group-error":
			JOptionPane.showMessageDialog(view, "Something went wrong creating group '" + argstr + "'", "Error", JOptionPane.ERROR_MESSAGE);
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
    public void createGroup(String name) throws IOException {
	server.write("create-group " + name);
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
    public void sendFindFriend(String key) throws IOException {
        server.write("findFriend " + key);
    }

    @Override
    public void sendAddFriend(int idUs) throws IOException {
        server.write("addFriend " + idUs);
    }

}
