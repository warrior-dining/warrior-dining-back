package warriordiningback.domain.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiriesAnswerRepository extends JpaRepository<InquiriesAnswer, Long> {

    Optional<InquiriesAnswer> findByInquiry(Inquiry inquiry);
}
