package warriordiningback.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "codes")
@Getter
@ToString
@NoArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String value;

    @Column(length = 100)
    private String comment;
    
    
    public Code(Long id) {
        this.id = id;
    }
}
