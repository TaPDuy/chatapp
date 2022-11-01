package nhom12.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.hibernate.util.JPAUtil;

public class Server {

    public static volatile ServerWorkerBus serverThreadBus;
    public static Socket clientSocket;

    public static void main(String[] args) throws IOException {
	
	JPAUtil.getEntityManagerFactory();
        ServerSocket listener = new ServerSocket(7777);
        serverThreadBus = new ServerWorkerBus();
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        // Mở một ServerSocket tại cổng 7777.
        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
        try {
            while (true) {
		
                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.
                clientSocket = listener.accept();
                ServerWorker serverThread = new ServerWorker(clientSocket, clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Số thread đang chạy là: "+serverThreadBus.getLength());
                executor.execute(serverThread);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                listener.close();
		JPAUtil.shutdown();
            } catch (IOException ex) {
                Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}