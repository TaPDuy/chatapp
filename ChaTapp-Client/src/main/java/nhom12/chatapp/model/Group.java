package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.HashSet;
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
public class Group implements Serializable {
    
    private static final long serialVersionUID = 2L;
    
    private int id;
    private String name;
    
    @Builder.Default
    private Set<User> members = new HashSet<>();

    public void addMember(User user) {
	members.add(user);
	user.getJoinedGroups().add(this);
    }
    
    @Override
    public String toString() {
	return 
	    "(Group) -> {\n"
		+ "\tid: " + this.id + ",\n"
		+ "\tname: '" + this.name + "',\n"
		+ "\tmembers: [\n"	
		+ this.members.stream()
		    .map(mem -> "\t" + mem.getUsername() + ",\n")
		    .reduce("", String::concat) 
		+ "],\n"
	    + "\n}";
    }
}
