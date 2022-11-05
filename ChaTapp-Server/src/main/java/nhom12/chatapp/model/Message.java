package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_message")
public class Message implements Serializable {
    
    private static final long serialVersionUID = 3L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="content")
    private String content;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="sender_id", referencedColumnName="id")
    private User sender;
    
    @ManyToMany
    @JoinTable(
	name="tbl_recipient", 
	joinColumns = @JoinColumn(name="message_id", referencedColumnName="id"), 
	inverseJoinColumns = @JoinColumn(name="recipient_id", referencedColumnName="id")
    )
    @Builder.Default
    private Set<User> recipients = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name="group_id", referencedColumnName="id")
    private Group group;
    
    @Column(
	name="time_sent", 
	nullable=false,
	updatable=false,
	insertable=false,
	columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSent;

    @Override
    public String toString() {
	return 
	    "(Message) -> {\n"
		+ "\tid: " + this.id + ",\n"
		+ "\tcontent: '" + this.content	+ "',\n" 
		+ "\tsender: " + this.sender.toString() + ",\n"
		+ "\treceiver: [\n"	
		+ this.recipients.stream()
		    .map(rec -> "\t" + rec.getUsername() + ",\n")
		    .reduce("", String::concat) 
		+ "],\n"
		+ "\tgroup: " + this.group.toString() + ",\n"
		+ "\ttimeSent: " + this.timeSent.toString()  
	    + "\n}";
    }
}
