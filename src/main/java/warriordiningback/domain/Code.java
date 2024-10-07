package warriordiningback.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import warriordiningback.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "codes")
@NoArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String value;

    @Column(length = 100)
    private String comment;

    @OneToMany(mappedBy = "gender")
    private List<User> users = new ArrayList<>();

}
