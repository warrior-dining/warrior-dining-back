package warriordiningback.domain.inquiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import warriordiningback.domain.BaseEntity;
import warriordiningback.domain.user.User;

@Entity
@Getter
@Table(name = "inquiry_answer")
public class InquiriesAnswer extends BaseEntity {

    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "inquiry_id", nullable = false)
    @JsonIgnore
    private Inquiry inquiry;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static InquiriesAnswer create(String content, Inquiry inquiry, User user) {
        InquiriesAnswer answer = new InquiriesAnswer();
        answer.content = content;
        answer.inquiry = inquiry;
        answer.user = user;
        return answer;
    }
}
