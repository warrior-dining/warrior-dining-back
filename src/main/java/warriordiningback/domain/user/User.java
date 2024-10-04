package warriordiningback.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import warriordiningback.domain.BaseEntity;
import warriordiningback.domain.Code;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String birth;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(name = "is_used")
    private boolean isUsed;

    @ManyToOne
    @JoinColumn(name = "code_id", nullable = false)
    private Code code;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /* 비즈니스 로직 */
    public static User create(String email, String password, String name, String birth, String phone, Code gender) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.name = name;
        user.birth = birth;
        user.phone = phone;
        user.isUsed = true;
        user.code = gender;
        return user;
    }
    /* ========== */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
