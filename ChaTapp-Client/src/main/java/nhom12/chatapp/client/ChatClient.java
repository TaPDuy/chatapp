package nhom12.chatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;

public class ChatClient {

    private Socket socket;
    
    private OutputStream serverOut;
    private InputStream serverIn;
    private ObjectOutputStream serverObjOut;
    private ObjectInputStream serverObjIn;
    private BufferedReader bufferedIn;
    public List<User> onlineUsers;
    public ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    
    public ChatClient() {
	this.onlineUsers = new ArrayList<>();
    }
    
    public void startMessageReader() {
        Thread t = new Thread() {
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        t.start();
    }
    
    private void readMessageLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = line.split(" ");
                
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                        
                    }
//                    } else if ("offline".equalsIgnoreCase(cmd)) {
//                        handleOffline(tokens);
//                    } else if ("msg".equalsIgnoreCase(cmd)) {
//                        String[] tokensMsg = StringUtils.split(line, null, 3);
//                        handleMessage(tokensMsg);
//                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void handleOffline(String[] tokens) {
        String viewName = tokens[1];
        String sdt = tokens[2];
        for(UserStatusListener listener : userStatusListeners) {
            listener.offline(viewName, sdt);
        }
    }

    private void handleOnline(String[] tokens) {
        String viewName = tokens[1];
        String sdt = tokens[2];
        for(UserStatusListener listener : userStatusListeners) {
            listener.online(viewName, sdt);
            User user = new User();
            user.setSdt(sdt);
            user.setUsername(viewName);
            onlineUsers.add(user);
        }
    }
    
    public boolean connect(String host, int port) {
	
	try {
	    socket = new Socket(host, port);
	    System.out.println("[INFO]: Connected to host " + host + ":" + port);
	    
	    serverIn = socket.getInputStream();
	    serverOut = socket.getOutputStream();
	    serverObjIn = new ObjectInputStream(serverIn);
	    serverObjOut = new ObjectOutputStream(serverOut);
	    
	    return true;
	} catch (IOException ex) {
	    System.out.println("[ERROR]: Couldn't connect to host " + host + ":" + port);
	}
	
	return false;
    }
    
    public boolean checkLogin(User user) {
        try {
	    
            serverOut.write("login\n".getBytes());
            serverObjOut.writeObject(user);
            
            bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            String response = bufferedIn.readLine();
	    System.out.println("[SERVER]: " + response);
	    return "ok login".equalsIgnoreCase(response);
	    
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
	return false;
    }
    
    public int addNewUser(User user) {
        try {
            String cmd = "register\n";
            serverOut.write(cmd.getBytes());
            
            serverObjOut.writeObject(user);
            serverIn = socket.getInputStream();
            bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            
            String response = bufferedIn.readLine();
            System.out.println("[SERVER]: " + response);
	    if("register success".equalsIgnoreCase(response)){
                return 1;
            }
            else if("error register".equalsIgnoreCase(response)){
                return 2;
            }
            else if("Number phone is existed".equalsIgnoreCase(response)){
                return 3;
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void logoff() throws IOException {
        String cmd = "logoff\n";
        serverOut.write(cmd.getBytes());
    }
}
