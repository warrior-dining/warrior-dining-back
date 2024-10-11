package warriordiningback.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import warriordiningback.domain.user.User;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "codes")
@Getter
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
    @JsonIgnore
    private List<User> users = new ArrayList<>();

}
