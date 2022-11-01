package nhom12.chatapp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerWorkerBus {
    
    private final List<ServerWorker> listServerThreads;

    public List<ServerWorker> getListServerThreads() {
        return listServerThreads;
    }

    public ServerWorkerBus() {
        listServerThreads = new ArrayList<>();
    }

    public void add(ServerWorker serverThread) {
        listServerThreads.add(serverThread);
    }
    
    public void mutilCastSend(String message) { 

	Server.serverThreadBus.getListServerThreads().forEach(serverThread -> {
	    try {
		serverThread.write(message);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	});
    }
    
    public void boardCast(String from, String message){
	
	Server.serverThreadBus.getListServerThreads().stream().filter(worker -> !worker.getUser().getUsername().equals(from)).forEach(worker -> {
	    try {
		worker.write(message);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	});
    }
    
    public void broadCastGroup(String from, String groupName, String msg) {
	
	listServerThreads.stream().filter(
	    worker -> !worker.getUser().getUsername().equals(from) && worker.getGroupNames().contains(groupName)
	).forEach(worker -> {
	    try {
		worker.write(msg);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	});
    }
    
    public int getLength() {
        return listServerThreads.size();
    }
    
    public void sendOnlineList() {
	
        String res = "";
	res = Server.serverThreadBus.getListServerThreads().stream().map(worker -> worker.getUser().getUsername() + "-").reduce(res, String::concat);
        Server.serverThreadBus.mutilCastSend("update-online-list " + res);
    }
    
    public void sendMessageToPersion(String to, String message) {
	
	Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getUsername().equals(to)).findAny();
	if(receiver.isPresent()) {
	    try {
		receiver.get().write(message);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
    public void remove(int id) {
	Server.serverThreadBus.getListServerThreads().removeIf(worker -> worker.getClientNumber() == id);
    }
}