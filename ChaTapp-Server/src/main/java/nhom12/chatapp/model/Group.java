package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
@Entity
@Table(name="tbl_group")
public class Group implements Serializable {
    
    private static final long serialVersionUID = 2L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="name")
    private String name;
    
    @ManyToMany
    @JoinTable(
	name="tbl_group_member",
	joinColumns = @JoinColumn(name="group_id", referencedColumnName="id"), 
	inverseJoinColumns = @JoinColumn(name="member_id", referencedColumnName="id")
    )
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
