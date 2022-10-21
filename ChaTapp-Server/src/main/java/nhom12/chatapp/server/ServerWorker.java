package nhom12.chatapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.server.dao.UserDAO;
import nhom12.chatapp.model.User;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String userName = null;
    
    private final HashSet<String> topicSet = new HashSet<>();
    private final UserDAO userDAO;
    
    private OutputStream clientOut;
    private InputStream clientIn;
    private ObjectOutputStream clientObjOut;
    private ObjectInputStream clientObjIn;

    public ServerWorker(Server server, Socket clientSocket) {
	
	this.server = server;
	this.clientSocket = clientSocket;
	this.userDAO = new UserDAO();
	
	try {    
	    this.clientOut = clientSocket.getOutputStream();
	    this.clientIn = clientSocket.getInputStream();
	    this.clientObjOut = new ObjectOutputStream(this.clientOut);
	    this.clientObjIn = new ObjectInputStream(this.clientIn);
	} catch (IOException ex) {
	    Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException, ClassNotFoundException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientIn));
        String line;
        while ((line = reader.readLine()) != null) {
	    
            System.out.println("[INFO]: Received command: " + line);
            String[] tokens = line.split(" ");
	    
            if (tokens != null && tokens.length > 0) {
		
                String cmd = tokens[0];
		
                if ("logoff".equals(cmd) || "quit".equalsIgnoreCase(cmd)) {
                    handleLogoff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    
                    User user = (User) clientObjIn.readObject();
                    System.out.println(user.getUserName());
                    handleLogin(clientOut, user);
                    
                } else if ("register".equalsIgnoreCase(cmd)) {
                    
                    User user = (User) clientObjIn.readObject();
                    handleRegister(clientOut, user);
		    
                } else if ("msg".equalsIgnoreCase(cmd)) {
                    String[] tokensMsg = line.split(" ", 3);
                    handleMessage(tokensMsg);
                } else if ("join".equalsIgnoreCase(cmd)) {
                    handleJoin(tokens);
                } else if ("leave".equalsIgnoreCase(cmd)) {
                    handleLeave(tokens);
                } else {
                    String msg = "unknown " + cmd + "\n";
                    clientOut.write(msg.getBytes());
                }
            }
        }

        clientSocket.close();
    }

    private void handleLeave(String[] tokens) {
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.remove(topic);
        }
    }

    public boolean isMemberOfTopic(String topic) {
        return topicSet.contains(topic);
    }

    private void handleJoin(String[] tokens) {
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.add(topic);
        }
    }

    // format: "msg" "login" body...
    // format: "msg" "#topic" body...
    private void handleMessage(String[] tokens) throws IOException {
        String sendTo = tokens[1];
        String body = tokens[2];

        boolean isTopic = sendTo.charAt(0) == '#';

        List<ServerWorker> workerList = server.getWorkerList();
        for (ServerWorker worker : workerList) {
            if (isTopic) {
                if (worker.isMemberOfTopic(sendTo)) {
                    String outMsg = "msg " + sendTo + ":" + userName + " " + body + "\n";
                    worker.send(outMsg);
                }
            } else {
                if (sendTo.equalsIgnoreCase(worker.getUserName())) {
                    String outMsg = "msg " + userName + " " + body + "\n";
                    worker.send(outMsg);
                }
            }
        }
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        List<ServerWorker> workerList = server.getWorkerList();

        // send other online users current user's status
        String onlineMsg = "offline " + userName + "\n";
        for (ServerWorker worker : workerList) {
            if (!userName.equals(worker.getUserName())) {
                worker.send(onlineMsg);
            }
        }
        clientSocket.close();
    }

    public String getUserName() {
        return userName;
    }

    private void handleLogin(OutputStream outputStream, User user) throws IOException {
	
        if (userDAO.checkLogin(user)) {
	    
            String msg = "ok login\n";
            outputStream.write(msg.getBytes());
	    
            userName = user.getUserName();
            System.out.println("[INFO]: User logged in: " + user.getUserName());

            List<ServerWorker> workerList = server.getWorkerList();

            // send current user all other online logins
            for (ServerWorker worker : workerList) {
                if (worker.getUserName() != null) {
                    if (!userName.equals(worker.getUserName())) {
                        String msg2 = "online " + worker.getUserName() + "\n";
                        send(msg2);
                    }
                }
            }

            // send other online users current user's status
            String onlineMsg = "online " + userName + "\n";
            for (ServerWorker worker : workerList) {
                if (!userName.equals(worker.getUserName())) {
                    worker.send(onlineMsg);
                }
            }
        } else {
	    
            String msg = "error login\n";
            outputStream.write(msg.getBytes());
            System.err.println("[ERROR]: User login failed: " + user.getUserName());
        }

    }

    private void handleRegister(OutputStream outputStream, User user) throws IOException {
        if(userDAO.insertUser(user)){
            String msg = "register success\n";
            outputStream.write(msg.getBytes());
        }
        else{
            String msg = "error register\n";
            outputStream.write(msg.getBytes());
        }
    }

    private void send(String msg) throws IOException {
        if (userName != null) {
            try {
                clientOut.write(msg.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
