package nhom12.chatapp.client.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.listener.WindowListener;
import nhom12.chatapp.client.view.GenericView;
import nhom12.chatapp.model.User;

public class ChatClient extends Thread implements MessageListener, WindowListener {

    private OutputStream os;
    private InputStream is;
    private Socket serverSocket;
    private List<String> onlineList;
    private ObjectOutputStream clientObjOut;
    private ObjectInputStream clientObjIn;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private int id;
    
    private GenericView view;

    public ChatClient(GenericView view) {
	onSwitch(view);
	
	onlineList = new ArrayList<>();
        id = -1;
    }
    
    public void setView(GenericView view) {
	this.view = view;
    }
    
    @Override
    public void run() {

	try {
	    
	    String message;
	    while (true) {
                            
		message = bufferedReader.readLine();
		if(message==null)
		    break;
		
		System.out.println("[SERVER]: " + message);
		
		String[] messageSplit = message.split(",");
		
//		switch (messageSplit[0]) {
//		    case "get-id":
//			setID(Integer.parseInt(messageSplit[1]));
//			view.setTitle("Client " + this.id);
//			break;
//		    case "update-online-list":
//			onlineList = new ArrayList<>();
//			String online = "";
//			String[] onlineSplit = messageSplit[1].split("-");
//			for (String onlineSplit1 : onlineSplit) {
//			    if (!onlineSplit1.equals("" + this.id))
//				onlineList.add("Client " + onlineSplit1);
//			    online += "Client " + onlineSplit1 + " đang online\n";
//			}	
//			view.getTextArea2().setText(online);
//			view.updateCombobox(onlineList);
//			break;
//		    case "global-message":
//			JTextArea txtArea = view.getTextArea1();
//			txtArea.setText(txtArea.getText()+messageSplit[1]+"\n");
//			txtArea.setCaretPosition(txtArea.getDocument().getLength());
//			break;
//		    default:
//			break;
//		}
	    }
	    
	    os.close();
	    is.close();
	    serverSocket.close();
	    
	} catch (UnknownHostException e) {
	} catch (IOException e) {
	}
    } 

    public void connect(String serverName, int serverPort) throws IOException {
	serverSocket = new Socket(serverName, serverPort);
	System.out.println("[INFO]: Connected to server " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getPort());
	os = serverSocket.getOutputStream();
        is = serverSocket.getInputStream();
        //clientObjIn = new ObjectInputStream(is);
        //clientObjOut = new ObjectOutputStream(os);
        bufferedReader = new BufferedReader(new InputStreamReader(is));
        //bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        //clientObjIn = new ObjectInputStream(serverSocket.getInputStream());
        //clientObjOut = new ObjectOutputStream(serverSocket.getOutputStream());
    }   
    
    private void write(String message) throws IOException {
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    
    private void setID(int id){
        this.id = id;
    }

    @Override
    public void sendGlobal(String msg) throws IOException {
	write("msg-global " + msg);
    }

    @Override
    public void send(String msg, String receiver) throws IOException {
	write("msg " + receiver + " " + msg);
    }

    @Override
    public boolean checkLogin(String viewname, String password) {
	boolean result = false;
	try {
	    System.out.println("[INFO]: Logging in with username '" + viewname + "'...");
	    write("login " + viewname + " " + password);
	    
	    String response = bufferedReader.readLine();
	    System.out.println("[SERVER]: " + response);
            System.out.println(1);
	    result = response.equalsIgnoreCase("ok-login");
            
	    if (result)
		System.out.println("[INFO]: Logged in successful.");
	    else
		System.out.println("[INFO]: Logged in failed.");
	    
	} catch (IOException ex) {
	    System.out.println("[ERROR]: Login failed caused by " + ex.getMessage());
	}
	return result;
    }

    @Override
    public int registerUser(User user) {
        
	try {
            write("register v");
            clientObjOut = new ObjectOutputStream(os);
            clientObjOut.writeObject(user);
            
            String response = bufferedReader.readLine();
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
            ex.printStackTrace();
        }
        return 0;
	
    }

    @Override
    public void onSwitch(GenericView newView) {
	newView.setMessageListener(this);
	newView.setWindowListener(this);
	this.view = newView;
    }

}
