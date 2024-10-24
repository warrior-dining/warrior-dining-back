package warriordiningback.domain.place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceQueryRepository extends JpaRepository<Place, Long> {

    @Query("SELECT p FROM Place p ORDER BY p.createdAt DESC LIMIT 5")
    List<Place> findTop5ByOrderByCreatedAtDESC();

    @Query("SELECT DISTINCT p FROM Place p " +
            "JOIN p.placeMenus pm " +
            "WHERE (p.name LIKE %:keyword% OR p.addressNew LIKE %:keyword% OR " +
            "pm.menu LIKE %:keyword%) " +
            "AND (:categoryId IS NULL OR p.code.id = :categoryId) " +
            "AND (:minPrice IS NULL OR pm.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR pm.price <= :maxPrice)")
    Page<Place> findAllByNameOrPlaceMenuOrAddressNew(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable);

    @Query("SELECT p FROM Place p JOIN p.placeMenus pm WHERE pm.price BETWEEN :minPrice AND :maxPrice")
    <T> Page<T> findAllByPriceRange(Long minPrice, Long maxPrice, Pageable pageable, Class<T> type);

    @Query("SELECT p FROM Place p JOIN p.placeMenus pm WHERE p.code.id = :codeId AND pm.price BETWEEN :minPrice AND :maxPrice")
    <T> Page<T> findAllByCategoryAndPriceRange(Long codeId, Long minPrice, Long maxPrice, Pageable pageable, Class<T> type);
}
