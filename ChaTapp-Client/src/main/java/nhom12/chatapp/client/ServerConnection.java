package nhom12.chatapp.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerConnection {

    private final BufferedWriter os;
    private final BufferedReader is;
    private final Socket serverSocket;
    
    public ServerConnection(String serverName, int serverPort) throws IOException {
	serverSocket = new Socket(serverName, serverPort);
	System.out.println("[INFO]: Connected to server " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getPort());
	os = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
	is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    }
    
    public void write(String msg) throws IOException {
	os.write(msg);
        os.newLine();
        os.flush();
    }

    public String readLine() throws IOException {
	return is.readLine();
    }
    
}
