package warriordiningback.domain.inquiry;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import warriordiningback.domain.BaseEntity;
import warriordiningback.domain.Code;
import warriordiningback.domain.user.User;

@Getter
@Entity
@Table(name = "inquiries")
public class Inquiry extends BaseEntity {

	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;

	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@ManyToOne
	@JoinColumn(name = "code_id")
	private Code code;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(mappedBy = "inquiry")
	private InquiriesAnswer answer;
	
	
   	public void updateCode(Code code) {
		this.code = code;
	}

   	public void inquiryUpdate(String title, String content) {
   		this.title = title;
   		this.content = content;
   	}
	
}
