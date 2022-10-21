package nhom12.chatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;

public class ChatClient {

    private Socket socket;
    
    private OutputStream serverOut;
    private InputStream serverIn;
    private ObjectOutputStream serverObjOut;
    private ObjectInputStream serverObjIn;
    
    public ChatClient() {
	
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
	
        BufferedReader bufferedIn;
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
    
    public boolean addNewUser(User user) {
	
        BufferedReader bufferedIn;
        try {
            String cmd = "register\n";
            serverOut.write(cmd.getBytes());
            
            serverObjOut.writeObject(user);
            serverIn = socket.getInputStream();
            bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            
            String response = bufferedIn.readLine();
            System.out.println("[SERVER]: " + response);
	    return "register success".equalsIgnoreCase(response);
            
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
	return false;
    }
}
