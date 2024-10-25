package warriordiningback.domain.inquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    Page<Inquiry> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Inquiry> findAllByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);

    Page<Inquiry> findAllByCreatedAtContainingOrderByCreatedAtDesc(LocalDate createdAt, Pageable pageable);

}
