package warriordiningback.domain.inquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.api.admin.dto.InquiryCountDto;

import java.util.List;

public interface InquiryQueryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i JOIN i.user u WHERE " +
            "u.name like %:userName% ORDER BY i.createdAt DESC ")
    Page<Inquiry> findAllByUserName(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT i FROM Inquiry i JOIN i.code c WHERE " +
            "c.value = :codeValue ORDER BY i.createdAt DESC ")
    Page<Inquiry> findAllByCodeValue(@Param("codeValue") String codeValue, Pageable pageable);

    @Query("SELECT new warriordiningback.api.admin.dto.InquiryCountDto(c.value, COUNT(i)) FROM Inquiry i JOIN i.code c GROUP BY c.value")
    List<InquiryCountDto> countInquiriesByCode();

    @Query("SELECT i FROM Inquiry i JOIN i.user u WHERE u.id = :userId ORDER BY i.createdAt DESC")
    Page<Inquiry> findByUser(@Param("userId") Long userId, Pageable pageable);
}
