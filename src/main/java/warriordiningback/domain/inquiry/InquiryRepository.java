package warriordiningback.domain.inquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import warriordiningback.domain.inquiry.Inquiry;
import warriordiningback.domain.review.Review;
import warriordiningback.domain.user.User;
import java.time.LocalDate;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>{
	
	Page<Inquiry> findAllByOrderByCreatedAtDesc(Pageable pageable);

	Page<Inquiry> findAllByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);
	Page<Inquiry> findAllByCreatedAtContainingOrderByCreatedAtDesc(LocalDate createdAt, Pageable pageable);

	@Query("SELECT i FROM Inquiry i JOIN i.user u WHERE "  +
			"u.name like %:userName% ORDER BY i.createdAt DESC ")
	Page<Inquiry> findAllByUserName(@Param("userName") String userName, Pageable pageable);

	@Query("SELECT i FROM Inquiry i JOIN i.code c WHERE " +
			"c.value = :codeValue ORDER BY i.createdAt DESC ")
	Page<Inquiry> findAllByCodeValue(@Param("codeValue") String codeValue, Pageable pageable);


}
