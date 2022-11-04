package nhom12.chatapp.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import nhom12.chatapp.client.ServerConnection;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.view.ClientView;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;

public class ChatClient extends Thread implements MessageListener {

    private final ServerConnection server;
    
    private List<String> onlineList;
    private int id;
    private User user;
    private List<User> userInsystem;
    
    private ClientView view;
    private Notification notification;

    public ChatClient(ServerConnection server) {
	this.server = server;
	this.user = new User();
	this.user.setViewName("guest");
	
	onlineList = new ArrayList<>();
        userInsystem = new ArrayList<>();
        notification = new Notification();
        id = -1;
    }

    public void setView(ClientView view) {
	this.view = view;
    }
    
    @Override
    public void run() {

	try {
	    
	    String message;
	    while (true) {

		message = server.read();

		if(message==null)
		    break;
		
		System.out.println("[SERVER]: " + message);
		
		String[] messageSplit = message.split(" ", 2);
		
		switch (messageSplit[0]) {
		    case "set-user":
			this.user = (User) server.readObject();
                        view.setUser(user);
			break;
		    case "update-online-list":
			String online = "";
			String[] onlineSplit = messageSplit[1].split("-");
			for (String onlineSplit1 : onlineSplit) {
			    if (!onlineSplit1.equals(this.user.getViewName()))
				onlineList.add("Client " + onlineSplit1);
			    online += "Client " + onlineSplit1 + " đang online\n";
			}	
			view.getTextArea2().setText(online);
			view.updateCombobox(onlineList);
			break;
                    case "notification-delete":
                        user = (User) server.readObject();
                        view.setUser(user);
                        view.updateCombobox(onlineList);
                        String[] mesDel = messageSplit[1].split(" ",4);
                        String timeDel = "Ngày " + mesDel[1] + " Lúc " + mesDel[2];
                        Notification notiDel = new Notification();
                        if(mesDel[0].equals(user.getViewName())){
                            notiDel.setActive("del");
                            notiDel.setContent(mesDel[3]);
                            notiDel.setTimeDate(timeDel);
                            view.setTableNotification(notiDel);
                        }
                        break;
                    case "notification-add":
                        notification = (Notification) server.readObject();
                        view.setTableNotification(notification);
                        break;

                    case "notification-confirm":
                        user = (User) server.readObject();
                        view.setUser(user);
                        view.updateCombobox(onlineList);
                        String[] mesCf = messageSplit[1].split(" ",4);
                        String timeCf = "Ngày " + mesCf[1] + " Lúc " + mesCf[2];
                        Notification notiCf = new Notification();
                        if(mesCf[0].equals(user.getViewName())){
                            notiCf.setActive("cf");
                            notiCf.setContent(mesCf[3]);
                            notiCf.setTimeDate(timeCf);
                            view.setTableNotification(notiCf);
                        }
                        break;
                    case "User-In-System":
                        this.userInsystem = (List<User>) server.readObject();
                        view.setTableUserSys(userInsystem);
                        break;
		    case "display":
			String[] args = messageSplit[1].split(" ", 2);
			JTextArea txtArea = view.getTextArea1();
			txtArea.setText(txtArea.getText() + "[" + args[0] + "]: " + args[1] + "\n");
			txtArea.setCaretPosition(txtArea.getDocument().getLength());
			break;
		    case "display-global":
			txtArea = view.getTextArea1();
			txtArea.setText(txtArea.getText() + "[SERVER]: " + messageSplit[1] + "\n");
			txtArea.setCaretPosition(txtArea.getDocument().getLength());
			break;
		    default:
			break;
		}
	    }
	    
	    server.close();
	    
	} catch (UnknownHostException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
	}

    }
    
    private void setID(int id){
        this.id = id;
    }

    @Override
    public void sendGlobal(String msg) throws IOException {
	server.write("msg-global " + msg);
    }

    @Override
    public void send(String msg, String receiver) throws IOException {
	server.write("msg " + receiver + " " + msg);
    }

    @Override
    public void sendDeleteFriend(User friendDel, String time) throws IOException {
        server.write("deletefriend " + time);
        server.writeObject(friendDel);
    }

    @Override
    public void sendFindFriend(String key) throws IOException {
        server.write("findFriend " + key);
    }

    @Override
    public void sendAddFriend(User userReceive, String time) throws IOException {
        server.write("addFriend " + time);
        server.writeObject(userReceive);
    }

    @Override
    public void sendConfirmAddFriend(User userSend, String time) throws IOException {
        server.write("confirmAddFriend "+time);
        server.writeObject(userSend);
    }

    @Override
    public void sendDeleteNotification(Notification delNotification) throws IOException {
        server.write("deleteNotification");
        server.writeObject(delNotification);
    }

}
