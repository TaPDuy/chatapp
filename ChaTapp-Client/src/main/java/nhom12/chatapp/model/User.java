package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private Date dob;
    private String sdt;
    private String address;
    private String status;
    private final Set<Group> joinedGroups = new HashSet<>();
    private final Set<User> friends = new HashSet<>();
    private final Set<User> friendsOfThis = new HashSet<>();

    @Override
    public String toString() {
	return
	    "(User) -> {\n"
		+ "\tid: " + this.id + ",\n"
		+ "\tviewname: '" + this.username + "',\n" 
		+ "\tpassword: " + this.password + ",\n"
		+ "\tfullname: " + this.fullname + ",\n"
		+ "\tgender: "	+ this.gender + ",\n"
		+ "\tdob: " + this.dob.toString() + ",\n"
		+ "\tsdt: " + this.sdt + ",\n"
		+ "\taddress: "	+ this.address + ",\n"
		+ "\tstatus: " + this.status + ",\n"
		+ "\tjoinedGroups: [\n"
		+ this.joinedGroups.stream()
		    .map(group -> "\t" + group.getName() + ",\n")
		    .reduce("", String::concat) 
		+ "],\n"
	    + "\n}";
    }
}
