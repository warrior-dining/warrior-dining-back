package warriordiningback.api.place.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import warriordiningback.domain.place.PlaceInfo;

@Tag(name="RestaurantController", description = "식당")
public interface PlaceControllerDocs {

    @Operation(summary = "Get방식", description = "식당 카테고리별 호출")
    public Page<PlaceInfo> placeList(
            @Parameter(name="categoryId", description = "카테고리 아이디", required = false) Long categoryId,
            @Parameter(name="minPrice", description = "최소 가격", required = false) Long minPrice,
            @Parameter(name="maxPrice", description = "최대 가격", required = false) Long maxPrice,
            @Parameter(name="pageable", description = "페이징 정보") Pageable pageable);

}
