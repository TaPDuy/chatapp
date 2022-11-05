package nhom12.chatapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            case "groups":
                break;
            case "online-users":
                break;
            case "users":
                break;
            case "create-invite":
                break;
            case "invite-response":
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
        
        if (
	    !Server.serverThreadBus.isOnline(viewname) && 
	    (this.user = userDAO.findByLoginInfo(viewname, password)) != null
	) {
            
            write("ok-login");
            
            ConsoleLogger.log("Login successfully with username: " + viewname, "CLIENT-" + clientNumber, ConsoleLogger.INFO);
	    
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
    
    private void loadOnlineNames() throws IOException {
	
	List<String> onlines = Server.serverThreadBus.getOnlineNames();
	
	String cmd = "update-online-list ";
	cmd = onlines.stream()
	    .map(name -> name + " ")
	    .reduce(cmd, String::concat)
	    .trim();
	write(cmd);
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
	    msgs.addAll(msgDAO.findByGroup(groupDAO.findByName(argstr.substring(1))));
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
        Server.serverThreadBus.sendOnlineList();
	
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
	    
	    Group group = groupDAO.findByName(args[0].substring(1));
	    msg.setGroup(group);
	    group.getMembers().forEach(msg.getRecipients()::add);
	    
	    if (msgDAO.save(msg))
		Server.serverThreadBus.broadCastGroup(user.getUsername(), args[0].substring(1), "display " + args[0] + " " + user.getUsername() + " " + args[1]);
	}
	else {
	    
	    msg.getRecipients().add(userDAO.findByUsername(args[0]));
	    
	    if (msgDAO.save(msg))
		Server.serverThreadBus.sendMessageToPersion(args[0], "display " + user.getUsername() + " " + args[1]);
	}
    }
    
    private void handleDeleteFriend(String argstr){
        
	User deleteUser = userDAO.findByUsername(argstr);
	if(user != null){
	    this.user.getFriends().remove(deleteUser);
	    
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
		Server.serverThreadBus.sendMessageToPersion(this.user.getUsername(), "add-notification", notSen);
		Server.serverThreadBus.sendMessageToPersion(deleteUser.getUsername(), "add-notification", notRec);
	    }
	}
    }
    
    private void handleCreateGroup(String argstr) throws IOException {
	
	Group group = Group.builder().name(argstr).build();
	group.addMember(user);
	if (groupDAO.checkExist(group)) {
	    write("group-existed " + argstr);
	} else {
	    if (groupDAO.save(group))
		write("group-created " + argstr);
	    else
		write("group-error " + argstr);
	}
    }
    
    private void handleJoin(String argstr) throws IOException {
	
	Group group = Group.builder().name(argstr).build();
//	group.getMembers().add(user);
	if (groupDAO.checkExist(group)) {
	    
	    if (!groupNames.contains(argstr)) {
		
//		if (groupUserDAO.insertGroupUser(group, this.user)) {
		if(false){
		    groupNames.add(argstr);
		    write("join-ok");
		    
		    ConsoleLogger.log(
			"Joined group: '" + argstr + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.INFO
		    );
		    
		} else {
		    
		    ConsoleLogger.log(
			"Something went wrong trying to join group: '" + argstr + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.ERROR
		    );
		    write("join-error");
		}
	    }
	    else {
		
		ConsoleLogger.log(
		    "Tried to join a group they already joined: '" + argstr + "'", 
		    "CLIENT-" + clientNumber, 
		    ConsoleLogger.ERROR
		);
		write("join-already");	
	    }
	    
	} else {
	    
	    ConsoleLogger.log(
		"Tried to join a non-existing group: '" + argstr + "'", 
		"CLIENT-" + clientNumber, 
		ConsoleLogger.ERROR
	    );
	    write("join-not-exist");
	}
	    
    }
    
    private void handleLeave(String argstr) throws IOException {
	
	Group group = new Group().builder().name(argstr).build();
	if (groupDAO.checkExist(group)) {
	    
	    if (groupNames.contains(argstr)) {
		
//		if (groupUserDAO.deleteGroupUser(group, this.user)) {
		if(false) {
		    groupNames.remove(argstr);
		    write("leave-ok");
		    
		    ConsoleLogger.log(
			"Left group: '" + argstr + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.INFO
		    );
		    
		} else {
		    
		    ConsoleLogger.log(
			"Something went wrong trying to leave group: '" + argstr + "'", 
			"CLIENT-" + clientNumber, 
			ConsoleLogger.ERROR
		    );
		    write("leave-error");
		}
	    } else {
		
		ConsoleLogger.log(
		    "Tried to leave a group they haven't joined: '" + argstr + "'", 
		    "CLIENT-" + clientNumber, 
		    ConsoleLogger.ERROR
		);
		write("leave-not-join");	
	    }
	    
	} else {
	    
	    ConsoleLogger.log(
		"Tried to leave a non-existing group: '" + argstr + "'", 
		"CLIENT-" + clientNumber, 
		ConsoleLogger.ERROR
	    );
	    write("leave-not-exist");
	}
	    
    }

    private void handleGetUserInSys(String key) {
//        List<User> userInSystem = new ArrayList<>();
//        userInSystem = userDAO.findAll();
//        System.out.println(userInSystem.size());
//        Server.serverThreadBus.sendUsInSys(user.getId(), userInSystem);
	List<User> userInSystem = userDAO.findByKey(key);
	ConsoleLogger.log(
	    "Found " + userInSystem.size() + " user(s) with key '" + key + "'", 
	    "CLIENT-" + clientNumber, 
	    ConsoleLogger.INFO
	);
	Server.serverThreadBus.sendUsInSys(user.getId(), userInSystem);
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

    private void handleAddFriend(String receiverName) {
	
	Notification not = Notification.builder()
		.content(this.user.getUsername() + " sent you a friend request")
		.sender(this.user)
		.recipient(userDAO.findByUsername(receiverName))
		.active("add")
		.build();
	
	if (notDAO.save(not))
	    Server.serverThreadBus.sendMessageToPersion(receiverName, "add-notification", not);
	// TODO: add an else
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
		Server.serverThreadBus.sendMessageToPersion(sender.getUsername(), "add-notification", notSen);
		Server.serverThreadBus.sendMessageToPersion(recipient.getUsername(), "add-notification", notRec);
		
		loadFriends();
		Server.serverThreadBus.sendLoadFriend(sender.getUsername());
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
