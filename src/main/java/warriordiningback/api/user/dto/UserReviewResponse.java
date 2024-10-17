package warriordiningback.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import warriordiningback.domain.place.Place;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewResponse {

	private Long id;
	private String placeName;
	private int rating;
	private String content;
	private boolean isDeleted;
	
}
