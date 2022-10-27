package nhom12.chatapp.server;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;
import nhom12.chatapp.server.dao.UserDAO;

public class ServerWorker implements Runnable {
    
    private final Socket clientSocket;
    private final int clientNumber;
//    private OutputStream os;
//    private InputStream is;
//    private BufferedReader bufferedReader;
//    private BufferedWriter bufferedWriter;
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
//            bufferedReader = new BufferedReader(new InputStreamReader(is));
            //bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
//            System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
//            write("get-id" + "," + this.clientNumber);

//            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            String cmd;
            while (!isClosed) {
                cmd = is.readUTF();
                if (cmd == null) {
                    break;
                }
                
                System.out.println("[CLIENT (" + (user != null ? user.getViewName() : "guest") + ")]: " + cmd);
                handleClientCmd(cmd);
//                String[] messageSplit = message.split(",");
//                if(messageSplit[0].equals("send-to-global")){
//                    Server.serverThreadBus.boardCast(this.getClientNumber(),"global-message"+","+"Client "+messageSplit[2]+": "+messageSplit[1]);
//                }
//                if(messageSplit[0].equals("send-to-person")){
//                    Server.serverThreadBus.sendMessageToPersion(Integer.parseInt(messageSplit[3]),"Client "+ messageSplit[2]+" (tới bạn): "+messageSplit[1]);
//                }

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
        String[] tokens = cmd.split(" ");
        
        switch (tokens[0]) {
            case "login":
                handleLogin(tokens);
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
            case "global-msg":
                Server.serverThreadBus.boardCast(user.getViewName(), "display " + user.getViewName() + " " + tokens[1]);
                break;
            case "msg":
                // Handle group later
                Server.serverThreadBus.sendMessageToPersion(tokens[1], "display " + user.getViewName() + " " + tokens[2]);
                break;
            case "logoff":
                handleLogoff();
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
    
    private void handleLogin(String[] tokens) throws IOException {
        
        if (tokens.length != 3) // Handle error
        {
            return;
        }
        
        String viewname = tokens[1];
        String password = tokens[2];
        
        if ((this.user = userDAO.checkLogin(viewname, password)) != null) {
            
            write("ok-login");
            
            System.out.println("[INFO]: User logged in: " + this.user.getViewName());
//
//            List<ServerWorker> workerList = server.getWorkerList();
//
//                // send current user all other online logins
//                for(ServerWorker worker : workerList) {
//                    if (worker.getViewName()!= null) {
//                        if (!sdt.equals(worker.getSdt())) {
//                            String msg2 = "online " + worker.getViewName()+ " " + worker.getSdt() + "\n";
//                            send(msg2);
//                        }
//                    }
//                }
//                // send other online users current user's status
//                String onlineMsg = "online " + viewName + " " + sdt + "\n";
//                for(ServerWorker worker : workerList) {
//                    if (!sdt.equals(worker.getSdt())) {
//                        worker.send(onlineMsg);
//                    }
//                }
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
    
    private void handleLogoff() throws IOException {
        isClosed = true;
        Server.serverThreadBus.boardCast(user.getViewName(), "display-server " + "User '" + user.getViewName() + "' logged off.");
        Server.serverThreadBus.remove(clientNumber);
        Server.serverThreadBus.sendOnlineList();
        clientSocket.close();
    }
}
