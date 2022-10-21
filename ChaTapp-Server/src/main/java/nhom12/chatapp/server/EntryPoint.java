package nhom12.chatapp.server;

public class EntryPoint {
    
    public static final int SERVER_PORT = 9999;
    
    public static void main(String[] args) {
	(new Server(SERVER_PORT)).start();
    }
}
