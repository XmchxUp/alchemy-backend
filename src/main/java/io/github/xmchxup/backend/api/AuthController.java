package io.github.xmchxup.backend.api;

import io.github.xmchxup.backend.core.security.JwtTokenUtils;
import io.github.xmchxup.backend.core.security.JwtUser;
import io.github.xmchxup.backend.dto.LoginRequest;
import io.github.xmchxup.backend.dto.SignUpRequest;
import io.github.xmchxup.backend.enumeration.RoleType;
import io.github.xmchxup.backend.exception.http.ForbiddenException;
import io.github.xmchxup.backend.exception.http.NotFoundException;
import io.github.xmchxup.backend.exception.http.ParameterException;
import io.github.xmchxup.backend.model.Role;
import io.github.xmchxup.backend.model.User;
import io.github.xmchxup.backend.repository.RoleRepository;
import io.github.xmchxup.backend.repository.UserRepository;
import io.github.xmchxup.backend.vo.ApiResponseVO;
import io.github.xmchxup.backend.vo.JwtAuthenticationResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Api(tags = "认证API")
@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @ApiOperation("登陆")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUser user = (JwtUser) authentication.getPrincipal();
        if (!user.isEnabled()) {
            throw new ForbiddenException(20006);
        }

        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getUsername(),
                user.getId().toString(),
                authorities,
                loginRequest.getRememberMe());
        return ResponseEntity.ok(new JwtAuthenticationResponseVO(token));
    }


    @PostMapping("/logout")
    @ApiOperation("登出")
    public ResponseEntity<Void> logout() {
        // TODO: 云服务器资源不足
        // token一但发布 就不能回收，直到过期
        // 上面rememberMe就是用来延长过期时间的
        // 解决方案可以使用Redis来保存user:token信息，每次判断token是否于redis中的是否相等
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("用户注册")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ParameterException(20002);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ParameterException(20003);
        }

        User user = User.builder()
                .nickname(signUpRequest.getNickname())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(
                        signUpRequest.getPassword()
                ))
                .build();


        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new NotFoundException(3011));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseVO(true, "用户注册成功"));
    }
}
