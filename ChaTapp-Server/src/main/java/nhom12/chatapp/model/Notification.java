package nhom12.chatapp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="tbl_notification")
public class Notification implements Serializable{
    
    private static final long serialVersionUID = 4L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="content")
    private String content;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="sender_id", referencedColumnName="id")
    private User sender;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="recipient_id", referencedColumnName="id")
    private User recipient;
    
    @Column(
	name="time_sent", 
	nullable=false,
	updatable=false,
	insertable=false,
	columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeDate;
    
    @Column(name="active")
    private String active;
}
