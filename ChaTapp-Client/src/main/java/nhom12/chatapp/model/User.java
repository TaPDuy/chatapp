package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    
    @Builder.Default
    private Set<Group> joinedGroups = new HashSet<>();
    
    @Builder.Default
    private Set<User> friends = new HashSet<>();
    
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();
    
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
