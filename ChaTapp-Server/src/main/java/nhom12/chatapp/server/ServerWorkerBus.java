package nhom12.chatapp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;

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
	
	Server.serverThreadBus.getListServerThreads().stream().filter(worker -> !worker.getUser().getViewName().equals(from)).forEach(worker -> {
	    try {
		worker.write(message);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	});
    }
    
    public void broadCastGroup(String from, String groupName, String msg) {
	
	listServerThreads.stream().filter(
	    worker -> !worker.getUser().getViewName().equals(from) && worker.getGroupNames().contains(groupName)
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
	res = Server.serverThreadBus.getListServerThreads().stream().map(worker -> worker.getUser().getViewName() + "-").reduce(res, String::concat);
        Server.serverThreadBus.mutilCastSend("update-online-list " + res);
    }
    
    public void sendMessageToPersion(String to, String message) {
	
	Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getViewName().equals(to)).findAny();
	//if(receiver.isPresent()) {
	    try {
		receiver.get().write(message);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	//}
    }
    
    public void sendDeleteFriendToPersion(int to, String nickNameF) {
	Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == to).findAny();
        if(receiver.isPresent()) {
            receiver.get().handleUpDateUser(nickNameF); 
        }
    }
    
    public void sendUsInSys(int from, List<User> usInSys){
        Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == from).findAny();
        receiver.get().writeUsInSys("User-In-System", usInSys);
    }
    
    public void remove(int id) {
	Server.serverThreadBus.getListServerThreads().removeIf(worker -> worker.getClientNumber() == id);
    }

    void sendNotificationAddFriend(int userf_id) {
        Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == userf_id).findAny();
        if(receiver.isPresent()) {
            receiver.get().handleSendNotificationAddFriend();
        }
    }
}