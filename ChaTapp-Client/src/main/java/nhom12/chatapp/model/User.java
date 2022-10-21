package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int id;
    private String sdt;
    private String userName;
    private String password;
    private String fullname;
    private String address;
    private Date dob;

    public User(String sdt, String password) {
        this.sdt = sdt;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
	return address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    } 
    
}
