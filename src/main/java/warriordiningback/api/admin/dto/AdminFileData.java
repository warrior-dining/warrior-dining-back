package warriordiningback.api.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warriordiningback.domain.place.Place;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminFileData {

	private Long id; // PK
	private Place place; // 기존 파일명
	private String name; // 서버에 저장할 새로운 파일명.
	private String extension; // 파일 확장자명.
	private String url; // 저장경로
	private String saveName; // 이미지 타입
	private String savePath; // 이미지 타입
	private String mediaType; // 이미지 타입
	private int viewOrder;
	
}
