package io.github.xmchxup.backend.core.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.xmchxup.backend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@AllArgsConstructor
public class JwtUser implements UserDetails {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    @JsonIgnore
    private String password;
    private Boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    public static JwtUser create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled() == null ? true : user.getEnabled(),
                authorities
        );
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return this.enabled;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtUser that = (JwtUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
