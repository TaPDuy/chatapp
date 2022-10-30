package nhom12.chatapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;
import nhom12.chatapp.server.dao.UserDAO;

public class ServerWorker implements Runnable {
    
    private final Socket clientSocket;
    private final int clientNumber;
    private boolean isClosed;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    
    private final UserDAO userDAO;
    private User user;
    
    public User getUser() {
        return this.user;
    }
    
    public int getClientNumber() {
        return this.clientNumber;
    }
    
    public ServerWorker(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
        
        System.out.println("Server thread number " + clientNumber + " Started");
        
        this.userDAO = new UserDAO();
        isClosed = false;
    }
    
    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            
            is = new ObjectInputStream(clientSocket.getInputStream());
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            String cmd;
            while (!isClosed) {
                cmd = is.readUTF();
                if (cmd == null) {
                    break;
                }        
                System.out.println("[CLIENT (" + (user != null ? user.getViewName() : "guest") + ")]: " + cmd);
                handleClientCmd(cmd);
            }
            
        } catch (IOException e) {
//            try {
//                            isClosed = true;
//            Server.serverThreadBus.remove(clientNumber);
//            System.out.println(this.clientNumber+" đã thoát");
//            Server.serverThreadBus.sendOnlineList();
//            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã thoát---");
//                handleLogoff();
//            } catch (IOException ex) {
//                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }
    
    public void write(String message) throws IOException {
        os.writeUTF(message);
        os.flush();
    }
    
    private void handleClientCmd(String cmd) throws IOException {
        String[] tokens = cmd.split(" ", 2);
        switch (tokens[0]) {
            case "login":
                handleLogin(tokens[1]);
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
//            case "addFriend":
//                handleAddFriend(tokens[1]);
//                break;
            case "deletefriend":
                int id_friend = Integer.parseInt(tokens[1]);
                handleDeleteFriend(id_friend);
                break;
            case "msg-global":
                Server.serverThreadBus.boardCast(user.getViewName(), "display " + user.getViewName() + " " + tokens[1]);
                break;
            case "msg":
                // Handle group later
                handleMsg(tokens[1]);
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
                break;
            case "leave":
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
        
        if ((this.user = userDAO.checkLogin(viewname, password)) != null) {
            
            write("ok-login");
            
            System.out.println("[INFO]: User logged in: " + this.user.getViewName());
	    
	    write("set-user");
	    os.writeObject(this.user);
	    os.flush();
	    
	    Server.serverThreadBus.sendOnlineList();
        } else {
            write("error-login");
            System.err.println("[ERROR]: User login failed: " + viewname);
        }
    }
    
    private void handleRegister(User user) {
        try {
            if (!userDAO.checkExist(user)) {
                if (userDAO.insertUser(user)) {
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
    
    private void handleUpDateUser(){
        System.out.println(user.getPassword());
        User userUpdate = new User();
        userUpdate = userDAO.checkLogin(user.getViewName(), user.getPassword());
        System.out.println(userUpdate.getId());
        try {
            write("set-user");
            os.writeObject(userUpdate);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void handleDeleteFriend(int id_friend){
        if(userDAO.deleteFriend(user, id_friend)){
           handleUpDateUser();
        }
        
    }
    
    private void handleLogoff() throws IOException {
        isClosed = true;
        Server.serverThreadBus.boardCast(user.getViewName(), "display-server " + "User '" + user.getViewName() + "' logged off.");
        Server.serverThreadBus.remove(clientNumber);
        Server.serverThreadBus.sendOnlineList();
        clientSocket.close();
    }
    
    private void handleMsg(String argstr) {
	String[] args = argstr.split(" ", 2);
	Server.serverThreadBus.sendMessageToPersion(args[0], "display " + user.getViewName() + " " + args[1]);
    }
//
//    private void handleAddFriend(String key) {
//        List<User> userInSystem = new ArrayList<>();
//        userInSystem = userDAO.getAllUser(key);
//        System.out.println(userInSystem.size());
//        try {
//            write("User-In-System");
//            os.writeObject(userInSystem);
//            os.flush();
//        } catch (IOException ex) {
//            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
