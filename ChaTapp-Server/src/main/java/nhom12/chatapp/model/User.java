package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name="tbl_user")
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private String password;
    
    @Column(name="full_name")
    private String fullname;
    
    @Column(name="gender")
    private String gender;
    
    @Column(name="dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    @Column(name="phone_number")
    private String sdt;
    
    @Column(name="address")
    private String address;
    
    @Column(name="status")
    private String status;
    
    @ManyToMany(mappedBy="members")
    @Builder.Default
    private Set<Group> joinedGroups = new HashSet<>();

    @ManyToMany
    @JoinTable(
	name="tbl_friendship",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="friend_id")
    )
    @Builder.Default
    private Set<User> friends = new HashSet<>();
    
    @OneToMany(mappedBy="recipient")
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
