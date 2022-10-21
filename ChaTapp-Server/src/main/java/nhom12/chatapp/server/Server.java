package nhom12.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
    
    private final int serverPort;
    private final ArrayList<ServerWorker> workerList = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while(true) {
		
                System.out.println("[INFO]: Listening for client connection...");
		
                Socket clientSocket = serverSocket.accept();
                System.out.println("[INFO]: Accepted connection from " + clientSocket);
		
                ServerWorker worker = new ServerWorker(this, clientSocket);
                workerList.add(worker);
                worker.start();
            }
        } catch (IOException e) {
            System.out.println("[ERROR]: Unexpected server error.");
	    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        workerList.remove(serverWorker);
    }
}
