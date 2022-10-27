package nhom12.chatapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {

    private ObjectOutputStream os;
    private ObjectInputStream is;
//    private final BufferedWriter os;
//    private final BufferedReader is;
    private final Socket serverSocket;
    
    public ServerConnection(String serverName, int serverPort) throws IOException {
	serverSocket = new Socket(serverName, serverPort);
	System.out.println("[INFO]: Connected to server " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getPort());
//	os = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
//	is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
	os = new ObjectOutputStream(serverSocket.getOutputStream());
	is = new ObjectInputStream(serverSocket.getInputStream());
    }
    
    public void write(String msg) throws IOException {
	os.writeUTF(msg);
        os.flush();
    }
    
    public void writeObject(Object obj) throws IOException {
	os.writeObject(obj);
	os.flush();
    }
    
    public Object readObject() throws IOException, ClassNotFoundException {
	return is.readObject();
    }

    public String read() throws IOException {
	return is.readUTF();
    }
    
    public void close() throws IOException {
	os.close();
	is.close();
	serverSocket.close();
    }
    
}
