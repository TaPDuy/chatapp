package nhom12.chatapp.client.controller;

import java.awt.Container;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
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
import nhom12.chatapp.model.Message;
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
    private String receiverName;
    
    private final HashMap<String, List<String>> loadedMessages;
    
    private ClientView view;
    private Notification notification;

    public ChatClient(ServerConnection server) {
	this.server = server;
	this.user = new User();
	this.user.setUsername("guest");
	
	onlineList = new ArrayList<>();
	friendList = new ArrayList<>();
	groupList = new ArrayList<>();
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
	if (receiverName != null) {
	    view.setChatBoxTitle("Đang nhắn với " + (receiverName.charAt(0) == '#' ? "nhóm " + receiverName.substring(1) : receiverName));
	    
	    // Load messages
	    if (loadedMessages.containsKey(this.receiverName)) {
		view.clearChatbox();
		view.printMessages(loadedMessages.get(this.receiverName));
	    } else {
		try {
		    server.write("load-messages " + this.receiverName);
		} catch (IOException ex) {
		    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	} else
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
                        view.setTitle("Logged in as " + this.user.getUsername());
			break;
		    case "update-online-list":
			if (argstr.isEmpty())
			    break;
			
			onlineList.clear();
			String online = "";
			String[] onlineSplit = argstr.split(" ");
			for (String onlineSplit1 : onlineSplit) {
			    onlineList.add(onlineSplit1);
			    online += "Client " + onlineSplit1 + " is online\n";
			}
			view.getTextArea2().setText(online);
			
			updateFriendList();
			break;
		    case "update-friends":
			friendList = argstr.isEmpty() ? new ArrayList<>() : Arrays.asList(argstr.split(" "));
			view.updateCombobox(friendList);
			updateFriendList();
			break;
		    case "update-groups":
			if (!argstr.isEmpty()) {
			
			    Stream<String> names = Arrays
				.stream(argstr.split("(?<=\")\\s(?=\")"))
				.map(name -> name.substring(1, name.length() - 2).replaceAll("\\\"", "\""));
			    groupList = names.collect(Collectors.toList());
			}
			view.updateGroupCombobox(groupList);
			updateGroupList();
			break;
		    case "update-notifications":
			notiList = (List<Notification>) server.readObject();
			updateNotificationList();
			break;
		    case "update-messages":
			List<Message> msgs = (List<Message>) server.readObject();
			
			List<String> msgStr = new ArrayList<>();
			msgs.forEach(msg -> {
			    String sender = msg.getSender().getUsername();
			    msgStr.add("[" + (sender.equals(this.user.getUsername()) ? "You" : sender) + "]: " + msg.getContent());
			});
			
			loadedMessages.put(argstr, msgStr);
			view.clearChatbox();
			view.printMessages(loadedMessages.get(argstr));
			break;
                    case "add-notification":
                        notification = (Notification) server.readObject();
			notiList.add(notification);
                        updateNotificationList();
                        break;
                    case "User-In-System":
                        List<User> userInsystem = (List<User>) server.readObject();
                        updateFriendResultList(userInsystem);
                        break;
		    case "group-in-system":
			List<String> results = (List<String>) server.readObject();
			updateGroupResultList(results);
			break;
		    case "display":
			String[] args = argstr.split(" ", 2);
			String receiver = args[0];
			if (loadedMessages.containsKey(receiver)) {
			    if (receiver.charAt(0) == '#') {
				args = args[1].split(" ", 2);
				loadedMessages.get(receiver).add("[" + args[0] + "]: " + args[1]);
			    } else
				loadedMessages.get(receiver).add("[" + receiver + "]: " + args[1]);
			    
			    if (receiver.equals(this.receiverName)) {
				view.clearChatbox();
				view.printMessages(loadedMessages.get(receiver));
			    }
			}
			break;
		    case "display-server":
			view.printMessage("[SERVER]: " + argstr);
			break;
		    case "view-profile":
			User profile = (User) server.readObject();
			
			String profileStr = ""
			    + "Username : " + profile.getUsername() + "\n" 
			    + "Full name : " + profile.getFullname() + "\n"
			    + "Gender : " + profile.getGender() + "\n"
			    + "DOB : " + new SimpleDateFormat("dd/MM/yyyy").format(profile.getDob()) + "\n"
			    + "Phone : " + profile.getSdt() + "\n"
			    + "Address : " + profile.getAddress() + "\n";
			
			JOptionPane.showMessageDialog(
				view, 
				profileStr, 
				user.getUsername() + "'s profile", 
				JOptionPane.PLAIN_MESSAGE
			);
			break;
		    case "view-members":
			String[] mem = argstr.split(" ");
			
			String msg = "Number of members: " + mem.length + "\nMembers:\n";
			for(int i = 0; i < mem.length; ++i) {
			    msg += mem[i] + ", ";
			    if (i % 8 == 0)
				msg += "\n";
			}
			
			JOptionPane.showMessageDialog(
				view, 
				msg, 
				user.getUsername() + "'s profile", 
				JOptionPane.PLAIN_MESSAGE
			);
			break;
		    case "group-created":
			JOptionPane.showMessageDialog(view, "Created group '" + argstr + "' successfully!", "Group created", JOptionPane.INFORMATION_MESSAGE);
			groupList.add(argstr);
			view.updateGroupCombobox(groupList);
			updateGroupList();
			break;
		    case "group-existed":
			JOptionPane.showMessageDialog(view, "Group '" + argstr + "' has already been created!", "Group existed", JOptionPane.ERROR_MESSAGE);
			break;
		    case "group-error":
			JOptionPane.showMessageDialog(view, "Something went wrong creating group '" + argstr + "'", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		    case "join-ok":
			groupList.add(argstr);
			view.updateGroupCombobox(groupList);
			updateGroupList();
			break;
		    case "join-error":
			JOptionPane.showMessageDialog(view, "Something went wrong joining group '" + argstr + "'", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		    case "join-already":
			JOptionPane.showMessageDialog(view, "You're already a member of group '" + argstr + "'", "Already in group", JOptionPane.ERROR_MESSAGE);
			break;
		    case "join-not-exist":
			JOptionPane.showMessageDialog(view, "Group '" + argstr + "' doesn't exist", "Group not found", JOptionPane.ERROR_MESSAGE);
			break;
		    case "leave-ok":
			groupList.remove(argstr);
			view.updateGroupCombobox(groupList);
			updateGroupList();
			break;
		    case "leave-error":
			JOptionPane.showMessageDialog(view, "Something went wrong leaving group '" + argstr + "'", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		    case "leave-already":
			JOptionPane.showMessageDialog(view, "You're not a member of group '" + argstr + "'", "Not in group", JOptionPane.ERROR_MESSAGE);
			break;
		    case "leave-not-exist":
			JOptionPane.showMessageDialog(view, "Group '" + argstr + "' doesn't exist", "Group not found", JOptionPane.ERROR_MESSAGE);
			break;
		    case "friend-request-error":
			JOptionPane.showMessageDialog(view, "Something went wrong sending friend request to '" + argstr + "'", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		    case "friend-request-already":
			JOptionPane.showMessageDialog(view, "You've already sent friend request to '" + argstr + "'", "Already requested", JOptionPane.ERROR_MESSAGE);
			break;
		    case "friend-already":
			JOptionPane.showMessageDialog(view, "You're already a friend of user '" + argstr + "'", "Already friend", JOptionPane.ERROR_MESSAGE);
			break;
		    case "friend-not-exist":
			JOptionPane.showMessageDialog(view, "User '" + argstr + "' doesn't exist", "User not found", JOptionPane.ERROR_MESSAGE);
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
    
    private void updateFriendList() {
	view.clearFriendList();
	
	friendList.forEach(friend -> {
	    view.addFriendRow(friend, onlineList.contains(friend) ? "online" : "offline");
	});
    }
    
    private void updateGroupList() {
	view.clearGroupList();
	
	groupList.forEach(name -> {
	    view.addGroupRow(name);
	});
    }
    
    private void updateNotificationList() {
	view.clearNotificationList();
	
	notiList.forEach(not -> {
	    view.addNotificationRow(
		not.getContent(), 
		new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(not.getTimeDate())
	    );
	});
    }
    
    private void updateGroupResultList(List<String> results) {
	view.clearGroupResultList();
	
	results.forEach(result -> {
	    String[] resSplit = result.split(" ");
	    view.addGroupResultRow(resSplit[0], resSplit[1]);
	});
    }
    
    private void updateFriendResultList(List<User> results) {
	view.clearFriendResultList();
	
	results.forEach(result -> {
	    view.addFriendResultRow(result.getUsername(), result.getFullname());
	});
    }

    @Override
    public void processNotification(int index) throws IOException {
	
	Notification not = notiList.get(index);
	
	if (not.getActive().equalsIgnoreCase("add")) {
		
	    String nameSend = not.getContent().split(" ")[0];
	    int choice = JOptionPane.showConfirmDialog(view, "Do you want confirm add friend with " + nameSend + " ?", "Ask", JOptionPane.YES_NO_OPTION);
	    if (choice == JOptionPane.YES_OPTION) {
		notiList.remove(not);
		updateNotificationList();
		server.write("confirmAddFriend "+ not.getId());
	    }
	}
	else {

	    int choice = JOptionPane.showConfirmDialog(view, "Do you want delete notification ?", "Ask", JOptionPane.YES_NO_OPTION);
	    if (choice == JOptionPane.YES_OPTION) {
		notiList.remove(not);
		updateNotificationList();
		server.write("deleteNotification " + not.getId());
	    }
	}
    }

    @Override
    public void processUnfriend(int index) throws IOException {
	
	String name = friendList.get(index);
	int choice = JOptionPane.showConfirmDialog(view, "Do you want delete friend " + name + " ?", "Ask", JOptionPane.YES_NO_OPTION);
	if (choice == JOptionPane.YES_OPTION) {
	    server.write("deletefriend " + name);
	}
    }

    @Override
    public void processJoinGroup(String groupName) throws IOException {
	
	int choice = JOptionPane.showConfirmDialog(view, "Do you want join group " + groupName + " ?", "Ask", JOptionPane.YES_NO_OPTION);
	if (choice == JOptionPane.YES_OPTION) {
	    server.write("join " + groupName);
	}
    }

    @Override
    public void processLeaveGroup(String groupName) throws IOException {
	
	int choice = JOptionPane.showConfirmDialog(view, "Do you want leave group " + groupName + " ?", "Ask", JOptionPane.YES_NO_OPTION);
	if (choice == JOptionPane.YES_OPTION) {
	    server.write("leave " + groupName);
	}
    }
    
    @Override
    public void processAddFriend(String friendName) throws IOException {
	
	int choice = JOptionPane.showConfirmDialog(view, "Do you want to befriend " + friendName + " ?", "Ask", JOptionPane.YES_NO_OPTION);
	if (choice == JOptionPane.YES_OPTION) {
	    server.write("addFriend " + friendName);
	}
    }

    @Override
    public void processViewProfile(int index) throws IOException {
	String name = friendList.get(index);
	server.write("view-profile " + name);
    }

    @Override
    public void processViewMembers(int index) throws IOException {
	String name = groupList.get(index);
	server.write("view-members " + name);
    }
    
    @Override
    public void processCreateGroup(String name) throws IOException {
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
		loadedMessages.get(this.receiverName).add("[You]: " + msg);
		view.printMessage("[You]: " + msg);
		server.write("msg " + receiverName + " " + msg);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void sendFindFriend(String key) throws IOException {
        server.write("findFriend " + key);
    }

    @Override
    public void sendFindGroup(String key) throws IOException {
	server.write("findGroup " + key);
    }

    @Override
    public void updateFriends() throws IOException {
	server.write("get-friends");
    }
}
