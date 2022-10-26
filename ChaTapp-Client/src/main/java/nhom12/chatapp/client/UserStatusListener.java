/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nhom12.chatapp.client;

/**
 *
 * @author Smile
 */
public interface UserStatusListener {
    public void online(String viewName, String sdt);
    public void offline(String viewName, String sdt);
}
