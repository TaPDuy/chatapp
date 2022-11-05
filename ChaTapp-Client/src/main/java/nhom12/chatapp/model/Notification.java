package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.Date;
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
public class Notification implements Serializable{
    
    private static final long serialVersionUID = 4L;
    
    private int id;
    private String content;
    private User sender;
    private User recipient;
    private Date timeDate;
    private String active;
}
