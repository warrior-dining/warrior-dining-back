package warriordiningback.domain.inquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import warriordiningback.domain.inquiry.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>{
	
	Page<Inquiry> findAllByOrderByIdDesc(Pageable pageable);

}
