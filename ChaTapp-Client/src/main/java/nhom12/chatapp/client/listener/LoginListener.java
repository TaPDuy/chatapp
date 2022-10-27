package nhom12.chatapp.client.listener;

import nhom12.chatapp.model.User;

public interface LoginListener {
    public boolean checkLogin(String viewname, String password);
    public int registerUser(User user);
}
