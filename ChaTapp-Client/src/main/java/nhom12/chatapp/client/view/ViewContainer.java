package nhom12.chatapp.client.view;

import nhom12.chatapp.client.listener.LoginListener;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.listener.WindowListener;

public class ViewContainer extends javax.swing.JFrame implements WindowListener {
    
    private LoginListener loginListener;
    private MessageListener messageListener;
    
    private Thread messageThread = new Thread();
    private Runnable messageLoop;
    
    public ViewContainer() {
	initComponents();
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
    @Override
    public void switchToView(String viewName) {
	switch(viewName) {
	    case "LoginFrm":
		if (loginListener != null) {
		    
		    if (messageThread.isAlive())
			messageThread.interrupt();
		    
		    setContentPane(new LoginFrm(this, loginListener));
		    pack();
		}
		break;
	    case "RegisterFrm":
		if (loginListener != null) {
		    
		    if (messageThread.isAlive())
			messageThread.interrupt();
		    
		    setContentPane(new RegisterFrm(this, loginListener));
		    pack();
		}
	    case "ClientView":
		if (messageLoop != null) {
		    messageThread = new Thread(messageLoop);
		    setContentPane(new ClientView(this, messageListener));
		    pack();
		    messageListener.setChatView(getContentPane());
		    messageThread.start();
		}
	    default:
		break;
	}
    }

    public void setLoginListener(LoginListener listener) {
	this.loginListener = listener;
    }

    public void setMessageListener(MessageListener messageListener) {
	this.messageListener = messageListener;
    }
    
    public void setMessageLoop(Runnable loop) {
	this.messageLoop = loop;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
