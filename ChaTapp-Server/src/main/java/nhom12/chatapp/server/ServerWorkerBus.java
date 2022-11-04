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
    
    public void sendMessageToPersion(String to, String msg, Object obj) {
	Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getUsername().equals(to)).findFirst();
	if(receiver.isPresent()) {
	    ServerWorker worker = receiver.get();
	    try {
		if(msg != null)
		    worker.write(msg);
		if(obj != null)
		    worker.writeObject(obj);
	    } catch (IOException ex) {
		Logger.getLogger(ServerWorkerBus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
    public void sendMessageToPersion(String to, String msg) {
	sendMessageToPersion(to, msg, null);
    }
    
    public void sendMessageToPersion(String to, Object obj) {
	sendMessageToPersion(to, null, obj);
    }
    
    public void sendDeleteFriendToPersion(User friendDel, String nickName, String timeDel) {
	Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == friendDel.getId()).findAny();
        if(receiver.isPresent()) {
            receiver.get().handleUpDateUser(friendDel, nickName, timeDel); 
        }
    }
    
    public void sendUsInSys(int from, List<User> usInSys){
        Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == from).findFirst();
        receiver.get().writeUsInSys("User-In-System", usInSys);
    }
    
    public void remove(int id) {
	Server.serverThreadBus.getListServerThreads().removeIf(worker -> worker.getClientNumber() == id);
    }

    void sendNotificationAddFriend(User userReceive, User userSend, String time) {
        Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == userReceive.getId()).findFirst();
        if(receiver.isPresent()) {
            receiver.get().handleSendNotificationAddFriend(userSend, time);
        }
    }

    void sendConfirmAddFriendToPersion(User userSend, String viewName, String timeCf) {
        Optional<ServerWorker> receiver = Server.serverThreadBus.getListServerThreads().stream().filter(worker -> worker.getUser().getId() == userSend.getId()).findFirst();
        if(receiver.isPresent()) {
            receiver.get().handleUpDateAddFUser(userSend, viewName, timeCf); 
        }
    }
}