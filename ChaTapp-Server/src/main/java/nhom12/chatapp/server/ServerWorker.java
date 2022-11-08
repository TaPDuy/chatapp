package nhom12.chatapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.model.Message;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;
import nhom12.chatapp.server.dao.GroupDAO;
import nhom12.chatapp.server.dao.MessageDAO;
import nhom12.chatapp.server.dao.NotificationDAO;
import nhom12.chatapp.server.dao.UserDAO;
import nhom12.chatapp.util.ConsoleLogger;

public class ServerWorker implements Runnable {
    
    private final Socket clientSocket;
    private final int clientNumber;
    private boolean isClosed;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    
    private final UserDAO userDAO;
    private final GroupDAO groupDAO;
    private final NotificationDAO notDAO;
    private final MessageDAO msgDAO;
    
    private User user;
    private List<String> groupNames;
    
    public User getUser() {
        return this.user;
    }
    
    public List<String> getGroupNames() {
	return this.groupNames;
    }
    
    public int getClientNumber() {
        return this.clientNumber;
    }
    
    public ServerWorker(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
        
	ConsoleLogger.log("Server thread started", "CLIENT-" + clientNumber, ConsoleLogger.INFO);
        
        this.userDAO = new UserDAO();
	this.groupDAO = new GroupDAO();
	this.notDAO = new NotificationDAO();
	this.msgDAO = new MessageDAO();
	
	this.user = User.builder().username("").build();
	
        isClosed = false;
    }
    
    @Override
    public void run() {
        try {
	    
            is = new ObjectInputStream(clientSocket.getInputStream());
            os = new ObjectOutputStream(clientSocket.getOutputStream());
	    
	    String cmd;
            while (!isClosed) {
		
                cmd = is.readUTF();
                if (cmd != null){
		    ConsoleLogger.log(cmd, "CLIENT-" + clientNumber, ConsoleLogger.INFO);
		    handleClientCmd(cmd);   
		}
            }
            
        } catch (IOException e) {
            try {
		ConsoleLogger.log("Logging off...", "CLIENT-" + clientNumber, ConsoleLogger.INFO);
		handleLogoff();
            } catch (IOException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void write(String message) throws IOException {
        os.writeUTF(message);
        os.flush();
    }
    
    public void writeObject(Object obj) throws IOException {
	os.writeObject(obj);
	os.flush();
    }

    private void handleClientCmd(String clientCmd) throws IOException {
        String[] tokens = clientCmd.split(" ", 2);
	String cmd = tokens[0];
	String args = tokens[1];
        
        switch (cmd) {
            case "login":
                handleLogin(args);
                break;
            case "register":
                User userGet;
                try {
                    userGet = (User) is.readObject();
                    handleRegister(userGet);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "findFriend":
                handleGetUserInSys(tokens[1]);
                break;
	    case "findGroup":
		handleFindGroup(args);
		break;
            case "addFriend":
                handleAddFriend(args);
                break;
            case "deletefriend":
                handleDeleteFriend(args);
                break;
            case "confirmAddFriend":
                handleConfirmAddFriend(Integer.parseInt(args));
                break;
            case "msg-global":
                Server.serverThreadBus.boardCast(user.getUsername(), "display " + user.getUsername()+ " " + args);
                break;
            case "msg":
                handleMsg(args);
                break;
            case "logoff":
                handleLogoff();
                break;
	    case "get-user":
		write("set-user");
		os.writeObject(this.user);
		os.flush();
		break;
            case "join":
		handleJoin(args);
                break;
            case "leave":
		handleLeave(args);
                break;
	    case "create-group":
		handleCreateGroup(args);
		break;
	    case "load-messages":
		loadMessages(args);
		break;
	    case "get-friends":
		loadFriends();
		break;
	    case "view-profile":
		User u = userDAO.findByUsername(args);
		if (u != null)
		    Server.serverThreadBus.sendMessageToPersion(this.user.getUsername(), "view-profile", u);
		break;
	    case "view-members":
		Optional<Group> group = groupDAO.findByName(args);
		if(group.isPresent()) {
		    
		    Set<User> members = group.get().getMembers();
		    String msg = "view-members "+group.get().getName()+" ";
		    msg = members.stream()
			.map(mem -> mem.getUsername() + " ")
			.reduce(msg, String::concat)
			.trim();
		    write(msg);
		}
		break;

            case "deleteNotification":
		handleDeleteNotification(Integer.parseInt(args));
                break;
            default:
                // Unknown command
                break;
        }
    }
    
    private void handleLogin(String argstr) throws IOException {
        
	String[] args = argstr.split(" ", 2);
        String viewname = args[0];
        String password = args[1];
	
	User login = userDAO.findByLoginInfo(viewname, password);
        
        if (!Server.serverThreadBus.isOnline(viewname) && login != null) {
            
            write("ok-login");
            
            ConsoleLogger.log("Login successfully with username: " + viewname, "CLIENT-" + clientNumber, ConsoleLogger.INFO);
	    
	    this.user = login;
	    
	    // Initialize worker and send post-login info to client
	    write("set-user");
	    os.writeObject(this.user);
	    os.flush();
	    
	    loadOnlineNames();
	    loadFriends();
	    loadGroupNames();
	    loadNotifications();

	    ConsoleLogger.log("Worker initialized", "CLIENT-" + clientNumber, ConsoleLogger.INFO);
	    
        } else {
            write("error-login");
	    ConsoleLogger.log("Login failed with username: " + viewname, "CLIENT-" + clientNumber, ConsoleLogger.ERROR);
        }
    }
    
    public void loadFriends() throws IOException {
	
	List<User> friends = userDAO.findFriends(this.user);
	
	String cmd = "update-friends ";
	cmd = friends.stream()
	    .map(friend -> friend.getUsername() + " ")
	    .reduce(cmd, String::concat)
	    .trim();
	write(cmd);
    }
    
    public void loadOnlineNames() throws IOException {
	
	List<String> onlines = Server.serverThreadBus.getOnlineNames();
	
	String cmd = "update-online-list ";
	cmd = onlines.stream()
	    .map(name -> name + " ")
	    .reduce(cmd, String::concat)
	    .trim();
	Server.serverThreadBus.mutilCastSend(cmd);
    }
    
    private void loadGroupNames() throws IOException {
	    
	groupNames = new ArrayList<>();
	this.user.getJoinedGroups().stream()
	    .map(group -> group.getName())
	    .forEach(groupNames::add);

	String cmd = "update-groups ";
	cmd = groupNames.stream()
	    .map(name -> "\"" + name.replace("\"", "\\\"") + "\\\" ")
	    .reduce(cmd, String::concat).trim();
	write(cmd);
    }
    
    private void loadNotifications() {
	
	List<Notification> nots = notDAO.findByRecipient(this.user);
	Server.serverThreadBus.sendMessageToPersion(this.user.getUsername(), "update-notifications", nots);
    }
    
    private void loadMessages(String argstr) {
	
	List<Message> msgs = new ArrayList<>();
	if (argstr.charAt(0) == '#') {
	    
	    Optional<Group> group = groupDAO.findByName(argstr.substring(1));
	    if(group.isPresent())
		msgs.addAll(msgDAO.findByGroup(group.get()));
	    
	} else {
	    User receiver = userDAO.findByUsername(argstr);
	    msgs.addAll(msgDAO.findByUser(this.user, receiver));
	}
	
	Server.serverThreadBus.sendMessageToPersion(this.user.getUsername(), "update-messages " + argstr, msgs);
    }
    
    private void handleRegister(User user) {
        try {
            if (!userDAO.checkExist(user)) {
                if (userDAO.save(user)) {
                    String msg = "register success";
                    write(msg);
                } else {
                    String msg = "error register";
                    write(msg);
                }
            } else {
                String msg = "Number phone is existed";
                write(msg);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleLogoff() throws IOException {
        isClosed = true;
        Server.serverThreadBus.boardCast(user.getUsername(), "display-server " + "User '" + user.getUsername() + "' logged off.");
        Server.serverThreadBus.remove(clientNumber);
        loadOnlineNames();
	
	userDAO.close();
	groupDAO.close();
	notDAO.close();
	msgDAO.close();
	
        clientSocket.close();
    }
    
    private void handleMsg(String argstr) {
	
	String[] args = argstr.split(" ", 2);
	Message msg = Message.builder()
		.content(args[1])
		.group(null)
		.sender(this.user)
		.build();
	
	if (args[0].charAt(0) == '#') {
	    
	    Optional<Group> group = groupDAO.findByName(args[0].substring(1));
	    if (group.isPresent()) {
		
		msg.setGroup(group.get());
		group.get().getMembers().forEach(msg.getRecipients()::add);

		if (msgDAO.save(msg))
		    Server.serverThreadBus.broadCastGroup(user.getUsername(), args[0].substring(1), "display " + args[0] + " " + user.getUsername() + " " + args[1]);
	    }
	}
	else {
	    
	    msg.getRecipients().add(userDAO.findByUsername(args[0]));
	    
	    if (msgDAO.save(msg))
		Server.serverThreadBus.sendMessageToPersion(args[0], "display " + user.getUsername() + " " + args[1]);
	}
    }
    
    private void handleCreateGroup(String argstr) throws IOException {
	
	Group group = Group.builder().name(argstr).build();
	group.addMember(user);
	if (groupDAO.checkExist(group)) {
	    write("group-existed " + argstr);
	} else {
	    if (groupDAO.save(group)) {
		groupNames.add(argstr);
		write("group-created " + argstr);
	    }
	    else
		write("group-error " + argstr);
	}
    }
    
    private void handleJoin(String groupName) throws IOException {
	
	Optional<Group> group = groupDAO.findByName(groupName);
	if(group.isPresent()) {
	    
	    if(!groupNames.contains(groupName)) {
		
		if (groupDAO.addMember(group.get(), this.user)) {
		    
		    groupNames.add(groupName);
		    write("join-ok " + groupName);

		    ConsoleLogger.log(
			"Joined group: '" + groupName + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.INFO
		    );
		    
		} else {
		    
		    ConsoleLogger.log(
			"Something went wrong trying to join group: '" + groupName + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.ERROR
		    );
		    write("join-error " + groupName);
		}
		
	    } else {
		
		ConsoleLogger.log(
		    "Tried to join a group they already joined: '" + groupName + "'", 
		    "CLIENT-" + clientNumber, 
		    ConsoleLogger.ERROR
		);
		write("join-already " + groupName);	
	    }
	    
	} else {
	    
	    ConsoleLogger.log(
		"Tried to join a non-existing group: '" + groupName + "'", 
		"CLIENT-" + clientNumber, 
		ConsoleLogger.ERROR
	    );
	    write("join-not-exist " + groupName);
	}
    }
    
    private void handleLeave(String groupName) throws IOException {
	
	Optional<Group> group = groupDAO.findByName(groupName);
	if(group.isPresent()) {
	    
	    if(groupNames.contains(groupName)) {
		
		if (groupDAO.deleteMember(group.get(), this.user)) {
		    
		    groupNames.remove(groupName);
		    write("leave-ok " + groupName);

		    ConsoleLogger.log(
			"Left group: '" + groupName + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.INFO
		    );
		    
		} else {
		    
		    ConsoleLogger.log(
			"Something went wrong trying to leave group: '" + groupName + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.ERROR
		    );
		    write("leave-error " + groupName);
		}
		
	    } else {
		
		ConsoleLogger.log(
		    "Tried to leave a group they're not a member of: '" + groupName + "'", 
		    "CLIENT-" + clientNumber, 
		    ConsoleLogger.ERROR
		);
		write("leave-already " + groupName);	
	    }
	    
	} else {
	    
	    ConsoleLogger.log(
		"Tried to leave a non-existing group: '" + groupName + "'", 
		"CLIENT-" + clientNumber, 
		ConsoleLogger.ERROR
	    );
	    write("leave-not-exist " + groupName);
	}
    }

    private void handleGetUserInSys(String key) {
	
	List<User> userInSystem = userDAO.findByKey(key);
	ConsoleLogger.log(
	    "Found " + userInSystem.size() + " user(s) with key '" + key + "'", 
	    "CLIENT-" + clientNumber, 
	    ConsoleLogger.INFO
	);
	Server.serverThreadBus.sendUsInSys(user.getId(), userInSystem);
    }
    
    private void handleFindGroup(String key) {
	
	List<Group> results = groupDAO.findByKey(key);
	ConsoleLogger.log(
	    "Found " + results.size() + " user(s) with key '" + key + "'", 
	    "CLIENT-" + clientNumber, 
	    ConsoleLogger.INFO
	);
	
	List<String> data = results.stream()
	    .map(group -> group.getName() + " " + group.getMembers().size())
	    .collect(Collectors.toList());
	Server.serverThreadBus.sendMessageToPersion(this.user.getUsername(), "group-in-system", data);
    }
    
    public void writeUsInSys(String mes, List<User> usInSys){
        try {
            write(mes);
            os.writeObject(usInSys);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleAddFriend(String friendName) throws IOException {

	User friend = userDAO.findByUsername(friendName);
	if(friend != null) {
	    
	    if (!userDAO.isFriend(this.user, friend)) {
		
		Notification not = Notification.builder()
		    .content(this.user.getUsername() + " sent you a friend request")
		    .sender(this.user)
		    .recipient(userDAO.findByUsername(friendName))
		    .active("add")
		    .build();
		
		if (!notDAO.checkRequestExist(this.user, friend)) {
		    
		    if (notDAO.save(not)) {
			notDAO.refresh(not);
			Server.serverThreadBus.sendMessageToPersion(friendName, "add-notification", not);
		    } else {
			
			ConsoleLogger.log(
			    "Already sent request to user: '" + friendName + "'", 
			    "CLIENT-" + clientNumber, 
			    ConsoleLogger.ERROR
			);
			write("friend-request-error " + friendName);
		    }
		    
		} else {
		    
		    ConsoleLogger.log(
			"Already sent request to user: '" + friendName + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.ERROR
		    );
		    write("friend-request-already " + friendName);
		}
		
	    } else {
		
		ConsoleLogger.log(
		    "Tried to befriend a friend: '" + friendName + "'", 
		    "CLIENT-" + clientNumber, 
		    ConsoleLogger.ERROR
		);
		write("friend-already " + friendName);
	    }
	    
	} else {
	    
	    ConsoleLogger.log(
		"Tried to befriend a non-existing user: '" + friendName + "'", 
		"CLIENT-" + clientNumber, 
		ConsoleLogger.ERROR
	    );
	    write("friend-not-exist " + friendName);
	}
    }

    public void handleUpDateAddFUser(User userSend, String nickName, String timeCf){
        User userUpdate = userDAO.findByLoginInfo(user.getUsername(), user.getPassword());
        try {
            write("notification-confirm "+userSend.getUsername()+ " " + timeCf + " "+nickName+" accept your friend request");
            os.writeObject(userUpdate);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleConfirmAddFriend(int notId) throws IOException {
	Optional<Notification> not = notDAO.findById(notId);
	if(not.isPresent()) {
	    User sender = not.get().getSender();
	    User recipient = not.get().getRecipient();
	    sender.getFriends().add(recipient);
	    recipient.getFriends().add(sender);
	    
	    Notification notSen = Notification.builder()
		.content(recipient.getUsername() + " is now your friend")
		.sender(recipient)
		.recipient(sender)
		.active("cf")
		.build();
		    
	    Notification notRec = Notification.builder()
		.content(sender.getUsername() + " is now your friend")
		.sender(sender)
		.recipient(recipient)
		.active("cf")
		.build();
	    
	    if(notDAO.save(Arrays.asList(notSen, notRec))) {
		notDAO.delete(not.get());
		
		notDAO.refresh(notRec);
		notDAO.refresh(notSen);
		Server.serverThreadBus.sendMessageToPersion(sender.getUsername(), "add-notification", notSen);
		Server.serverThreadBus.sendMessageToPersion(recipient.getUsername(), "add-notification", notRec);
		
		loadFriends();
		Server.serverThreadBus.sendLoadFriend(sender.getUsername());
	    }
	}
    }
    
    private void handleDeleteFriend(String argstr) throws IOException {
        
	User deleteUser = userDAO.findByUsername(argstr);
	if(user != null){
	    
	    userDAO.deleteFriend(deleteUser, this.user);
	    
	    Notification notSen = Notification.builder()
		.content(deleteUser.getUsername() + " is no longer your friend")
		.sender(deleteUser)
		.recipient(this.user)
		.active("cf")
		.build();
		    
	    Notification notRec = Notification.builder()
		.content(this.user.getUsername() + " is no longer your friend")
		.sender(this.user)
		.recipient(deleteUser)
		.active("cf")
		.build();
	    
	    if(notDAO.save(Arrays.asList(notSen, notRec))) {
		notDAO.refresh(notRec);
		notDAO.refresh(notSen);
		Server.serverThreadBus.sendMessageToPersion(this.user.getUsername(), "add-notification", notSen);
		Server.serverThreadBus.sendMessageToPersion(deleteUser.getUsername(), "add-notification", notRec);
		
		loadFriends();
		Server.serverThreadBus.sendLoadFriend(deleteUser.getUsername());
	    }
	}
    }

    private void handleDeleteNotification(int notId) {
	Optional<Notification> not = notDAO.findById(notId);
	if(not.isPresent()) {
	    notDAO.delete(not.get());
	}
    }
}
