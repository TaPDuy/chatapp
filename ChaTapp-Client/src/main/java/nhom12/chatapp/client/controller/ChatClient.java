package nhom12.chatapp.client.controller;

import java.awt.Container;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import nhom12.chatapp.client.ServerConnection;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.view.ClientView;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;

public class ChatClient implements MessageListener, Runnable {

    private final ServerConnection server;
    
    private List<String> onlineList;
    private List<String> groupList;
    private List<String> friendList;
    private List<Notification> notiList;
    
    private int id;
    private User user;
    private List<User> userInsystem;
    private String receiverName;
    
    private HashMap<String, List<String>> loadedMessages;
    
    private ClientView view;
    private Notification notification;

    public ChatClient(ServerConnection server) {
	this.server = server;
	this.user = new User();
	this.user.setUsername("guest");
	
	onlineList = new ArrayList<>();
	groupList = new ArrayList<>();
        userInsystem = new ArrayList<>();
	loadedMessages = new HashMap<>();
	notiList = new ArrayList<>();
        notification = new Notification();
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
		System.out.println(argstr);
		
		switch (cmd) {
		    case "set-user":
			this.user = (User) server.readObject();
                        view.setUser(user);
			break;
		    case "update-online-list":
			if (argstr.isEmpty())
			    break;
			
			onlineList.clear();
			String online = "";
			String[] onlineSplit = argstr.split(" ");
			for (String onlineSplit1 : onlineSplit) {
			    if (!onlineSplit1.equals(this.user.getUsername()))
				onlineList.add(onlineSplit1);
			    online += "Client " + onlineSplit1 + " is online\n";
			}
			view.getTextArea2().setText(online);
			break;
		    case "update-friends":
			if (argstr.isEmpty())
			    break;
			
			friendList = Arrays.asList(argstr.split(" "));
			view.updateCombobox(friendList);
			break;
		    case "update-groups":
			if (argstr.isEmpty())
			    break;
			
			Stream<String> names = Arrays
			    .stream(argstr.split("(?<=\")\\s(?=\")"))
			    .map(name -> name.substring(1, name.length() - 2).replaceAll("\\\"", "\""));
			groupList = names.collect(Collectors.toList());
			view.updateGroupCombobox(groupList);
			break;
		    case "update-notifications":
			notiList = (List<Notification>) server.readObject();
			view.setTableNotification(notiList);
			break;
                    case "add-notification":
                        notification = (Notification) server.readObject();
			notiList.add(notification);
                        view.setTableNotification(notiList);
                        break;
                    case "User-In-System":
                        this.userInsystem = (List<User>) server.readObject();
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
			groupList.add(argstr);
			view.updateGroupCombobox(groupList);
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
    public void processNotification(int index) throws IOException {
	
	Notification not = notiList.get(index);
	
	if (not.getActive().equalsIgnoreCase("add")) {
		
	    String nameSend = not.getContent().split(" ")[0];
	    int choice = JOptionPane.showConfirmDialog(view, "Do you want confirm add friend with " + nameSend + " ?", "Ask", JOptionPane.YES_NO_OPTION);
	    if (choice == JOptionPane.YES_OPTION) {
		notiList.remove(not);
		view.setTableNotification(notiList);
		server.write("confirmAddFriend "+ not.getId());
	    }
	}
	else {

	    int choice = JOptionPane.showConfirmDialog(view, "Do you want delete notification ?", "Ask", JOptionPane.YES_NO_OPTION);
	    if (choice == JOptionPane.YES_OPTION) {
		notiList.remove(not);
		view.setTableNotification(notiList);
		server.write("deleteNotification " + not.getId());
	    }
	}
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
    public void sendDeleteFriend(String friendName) throws IOException {
        server.write("deletefriend " + friendName);
    }

    @Override
    public void sendFindFriend(String key) throws IOException {
        server.write("findFriend " + key);
    }

    @Override
    public void sendAddFriend(String receiverName) throws IOException {
        server.write("addFriend " + receiverName);
    }
}
