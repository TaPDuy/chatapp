package nhom12.chatapp.model;

import java.io.Serializable;

public class Group implements Serializable {
    
    private static final long serialVersionUID = 2L;
    
    private int id;
    private String name;

    public Group() {
    
	this(0, "");
    }

    public Group(int id, String name) {
	this.id = id;
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public Group setId(int id) {
	this.id = id;
	return this;
    }

    public String getName() {
	return name;
    }

    public Group setName(String name) {
	this.name = name;
	return this;
    }

    @Override
    public String toString() {
	return "Group: {id: " + this.id + ", name: '" + this.name + "'}";
    }
}
