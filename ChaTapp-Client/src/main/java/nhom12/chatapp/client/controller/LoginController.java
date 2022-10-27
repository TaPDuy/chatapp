package nhom12.chatapp.client.controller;

import java.io.IOException;
import nhom12.chatapp.client.ServerConnection;
import nhom12.chatapp.client.listener.LoginListener;
import nhom12.chatapp.client.view.LoginFrm;
import nhom12.chatapp.model.User;

public class LoginController implements LoginListener {

    private final LoginFrm loginFrm;
    private final ServerConnection server;
    
    public LoginController(LoginFrm loginFrm, ServerConnection server) {
	this.loginFrm = loginFrm;
	this.server = server;
    }
    
    @Override
    public boolean checkLogin(String viewname, String password) {
	boolean result = false;
	try {
	    System.out.println("[INFO]: Logging in with username '" + viewname + "'...");
	    server.write("login " + viewname + " " + password);
	    
	    String response = server.readLine();
	    System.out.println("[SERVER]: " + response);
	    result = response.equals("ok-login");
	    if (result)
		System.out.println("[INFO]: Logged in successful.");
	    else
		System.out.println("[INFO]: Logged in failed.");
	    
	} catch (IOException ex) {
	    System.out.println("[ERROR]: Login failed caused by " + ex.getMessage());
	}
	return result;
    }

    @Override
    public int registerUser(User user) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
