package warriordiningback.api.user.oauth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import warriordiningback.domain.user.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class CustomOAuthUser implements OAuth2User {

    private User user;
    private Long flag;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grant = new HashSet<>();
        if (user != null) {
            user.getRoles().forEach(role -> grant.add(new SimpleGrantedAuthority(role.getRole())));
        }
        return grant;
    }

    @Override
    public String getName() {
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

}
