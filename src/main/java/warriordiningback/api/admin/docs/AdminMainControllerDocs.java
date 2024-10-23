package warriordiningback.api.admin.docs;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="AdminMainController", description = "관리자 메인")
public interface AdminMainControllerDocs {

    /***************************************************
     * @Operation 메소드 정의
     * operationId: Endpoint 아이디
     * summary: 간단한 설명으로 Swagger-ui의 Endpoint 상단에 노출
     * description: 엔드포인트 상세 설명
     * tags: 현재 Endpoint가 어떠한 tag 그룹에 속한지 알려주는 속성
     * response: 응답코드, 응답 타입
     * security: 보안방법에 대한 설정
     * *************************************************/

    /***************************************************
     * @Parameters 파라메터 정의
     * name : 파라미터 이름
     * description : 파라미터 설명
     * required : 필수/선택 여부
     * in : 파라미터의 타입 설정
     * ParameterIn.QUERY : 요청 쿼리 파라미터
     * ParameterIn.HEADER : 요청 헤더에 전달되는 파라미터
     * ParameterIn.PATH: PathVariable 에 속하는 파라미터
     * 값없음: RequestBody에 해당하는 객체 타입의 파라미터
     * *************************************************/

    /***************************************************
     * @ApiResponse 응답 정의
     * responseCode: HTTP 상태코드
     * description : 응답 결과 구조에 대한 설명
     * content : 응답 페이로드 구조
     * schema : 페이로드에서 사용하는 객체 스키마
     * *************************************************/

    @Operation(summary = "Get방식", description = "루트 경로 호출")
    //@ApiResponse(responseCode = "200", description = "Get 응답 성공", content = @Content(schema = @Schema(implementation = Map.class)))
    public Map<String, Object> mainPage();

}
