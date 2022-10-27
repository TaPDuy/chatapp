
package nhom12.chatapp.client.controller;

import java.awt.Window;
import nhom12.chatapp.client.ServerConnection;
import nhom12.chatapp.client.listener.WindowListener;
import nhom12.chatapp.client.view.ClientView;
import nhom12.chatapp.client.view.LoginFrm;
import nhom12.chatapp.client.view.RegisterFrm;

public class WindowController implements WindowListener {

    private final ServerConnection server;
    private Window currentWindow;
    
    // Controllers
    private final LoginController loginCtrl;
    private final ChatClient chatCtrl;
    
    public WindowController(ServerConnection server) {
	this.server = server;
	
	this.chatCtrl = new ChatClient(server);
	this.loginCtrl = new LoginController(server);
	
	onSwitchFrame("LoginFrm");
    }

    public Window getCurrentWindow() {
	return currentWindow;
    }
    
    @Override
    public void onSwitchFrame(String newComponentName) {
	
	if(currentWindow != null)
	    currentWindow.dispose();
	
	switch (newComponentName) {
	    case "ClientView":
		ClientView view = new ClientView();
		chatCtrl.setView(view);
		view.setMessageListener(chatCtrl);
		view.setWindowListener(this);
		view.setVisible(true);
		currentWindow = view;
		chatCtrl.start();
		break;
	    case "LoginFrm":
		LoginFrm frm = new LoginFrm();
		loginCtrl.setLoginForm(frm);
		frm.setLoginListener(loginCtrl);
		frm.setWindowListener(this);
		frm.setVisible(true);
		currentWindow = frm;
		break;
	    case "RegisterFrm":
		RegisterFrm regfrm = new RegisterFrm();
		loginCtrl.setRegistryForm(regfrm);
		regfrm.setLoginListener(loginCtrl);
		regfrm.setWindowListener(this);
		regfrm.setVisible(true);
		currentWindow = regfrm;
		break;
	    default:
		break;
	}
    }
     
}
