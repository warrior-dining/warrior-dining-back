package warriordiningback.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInquiryResponse {

	private Long id;
	private String title;
	private String content;
	
}
