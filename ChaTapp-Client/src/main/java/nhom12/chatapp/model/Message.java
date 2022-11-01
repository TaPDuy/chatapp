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
public class Message implements Serializable {
    
    private static final long serialVersionUID = 3L;
    
    private int id;
    private String content;
    private User sender;
    private final Set<User> recipients = new HashSet<>();
    private Group group;
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
