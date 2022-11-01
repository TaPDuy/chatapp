package nhom12.chatapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.model.User;
import nhom12.chatapp.server.dao.GroupDAO;
//import nhom12.chatapp.server.dao.GroupUserDAO;
import nhom12.chatapp.server.dao.UserDAO;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;

public class ServerWorker implements Runnable {
    
    private final Socket clientSocket;
    private final int clientNumber;
    private boolean isClosed;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    
    private final UserDAO userDAO;
    private final GroupDAO groupDAO;
//    private final GroupUserDAO groupUserDAO;
    
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
//	this.groupUserDAO = new GroupUserDAO(this.groupDAO);
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
                int idUs = Integer.parseInt(tokens[1]);
                System.out.println(idUs);
                handleAddFriend(idUs);
            case "deletefriend":
                int id_friend = Integer.parseInt(tokens[1]);
                handleDeleteFriend(id_friend);
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
            case "notifications":
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
        
        if ((this.user = userDAO.findByLoginInfo(viewname, password)) != null) {
            
            write("ok-login");
            
            ConsoleLogger.log("Login successfully with username: " + viewname, "CLIENT-" + clientNumber, ConsoleLogger.INFO);
	    
	    // Initialize worker and send post-login info to client
	    write("set-user");
	    os.writeObject(this.user);
	    os.flush();
	    
	    Server.serverThreadBus.sendOnlineList();
	    
	    // TODO: Sends group list to client
//	    List<Group> groups = groupUserDAO.getGroupsByUser(this.user);
//	    groupNames = new ArrayList<>();
//	    groups.stream().map(group -> group.getName()).forEach(groupNames::add);

	    ConsoleLogger.log("Worker initialized", "CLIENT-" + clientNumber, ConsoleLogger.INFO);
	    
        } else {
            write("error-login");
	    ConsoleLogger.log("Login failed with username: " + viewname, "CLIENT-" + clientNumber, ConsoleLogger.ERROR);
        }
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
    
    public void handleUpDateUser(String nickNameF){
        User userUpdate = new User();
        userUpdate = userDAO.findByLoginInfo(user.getUsername(), user.getPassword());
        System.out.println(userUpdate.getId());
	
        try {
            write("notification-delete "+nickNameF+" "+userUpdate.getUsername()+" delete friend with you");
            os.writeObject(userUpdate);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void handleDeleteFriend(int id_friend){

        User fUser = new User();
        for(User friend: user.getFriends()){
            if(id_friend==friend.getId()){
                fUser = friend;
            }
        }
        if(userDAO.deleteFriend(user, id_friend)){
            handleUpDateUser(fUser.getUsername());
            Server.serverThreadBus.sendDeleteFriendToPersion(id_friend, fUser.getUsername());
        }
        
    }
    
    private void handleLogoff() throws IOException {
        isClosed = true;
        Server.serverThreadBus.boardCast(user.getUsername(), "display-server " + "User '" + user.getUsername() + "' logged off.");
        Server.serverThreadBus.remove(clientNumber);
        Server.serverThreadBus.sendOnlineList();
        clientSocket.close();
    }
    
    private void handleMsg(String argstr) {
	String[] args = argstr.split(" ", 2);
	
	if (args[0].charAt(0) == '#')
	    Server.serverThreadBus.broadCastGroup(user.getUsername(), args[0].substring(1), "display " + user.getUsername() + " " + args[1]);
	else
	    Server.serverThreadBus.sendMessageToPersion(args[0], "display " + user.getUsername() + " " + args[1]);
    }
    
    private void handleCreateGroup(String argstr) throws IOException {
	
	Group group = new Group().builder().name(argstr).build();
	if (groupDAO.checkExist(group)) {
	    write("group-existed");
	} else {
	    if (groupDAO.save(group))
		write("group-created");
	    else
		write("group-error");
	}
    }
    
    private void handleJoin(String argstr) throws IOException {
	
	Group group = new Group().builder().name(argstr).build();
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
        List<User> userInSystem = new ArrayList<>();
        userInSystem = userDAO.findAll();
        System.out.println(userInSystem.size());
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

    private void handleAddFriend(int userf_id) {
        if(userDAO.insertFriend(user, userf_id)){
            Server.serverThreadBus.sendNotificationAddFriend(userf_id);
        }
    }

    public void handleSendNotificationAddFriend(){
        User userUpdate = new User();
        userUpdate = userDAO.findByLoginInfo(user.getUsername(), user.getPassword());
        System.out.println(userUpdate.getFriends().size());
        try {
            write("notification-add "+user.getUsername()+" send add friend for you");
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
